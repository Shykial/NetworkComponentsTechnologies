package p.lodz.tul.restadapter.endpoints;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.tul.applicationports.service.CreateRentUseCase;
import p.lodz.tul.applicationports.service.EndRentUseCase;
import p.lodz.tul.applicationports.service.GetRentsUseCase;
import p.lodz.tul.applicationports.service.UpdateRentUseCase;
import p.lodz.tul.restadapter.dto.RentDTO;
import p.lodz.tul.restadapter.mappers.AccountMapper;
import p.lodz.tul.restadapter.mappers.RentMapper;
import p.lodz.tul.restadapter.mappers.VehicleMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
public class RentController {
    private final CreateRentUseCase createRentUseCase;
    private final UpdateRentUseCase updateRentUseCase;
    private final GetRentsUseCase getRentsUseCase;
    private final EndRentUseCase endRentUseCase;

    @Autowired
    public RentController(CreateRentUseCase createRentUseCase, UpdateRentUseCase updateRentUseCase, GetRentsUseCase getRentsUseCase, EndRentUseCase endRentUseCase) {
        this.createRentUseCase = createRentUseCase;
        this.updateRentUseCase = updateRentUseCase;
        this.getRentsUseCase = getRentsUseCase;
        this.endRentUseCase = endRentUseCase;
    }

    @PostMapping(value = "/rent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addRent(RentDTO rent) {
        if (rent.getAccountDTO() == null || rent.getVehicleDTO() == null) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        UUID rentUUID;
        if (rent.getEndDate() == null) {
            if (rent.getStartDate() == null) {
                rentUUID = createNotStartedRentAndGetUUID(rent);
            } else {
                rentUUID = createStartedRentAndGetUUID(rent);
            }
        } else {
            rentUUID = createFinishedRentAndGetUUID(rent);
        }
        return ResponseEntity.status(CREATED).body(rentUUID.toString());
    }

    @GetMapping(value = "{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> rentByUUID(@PathVariable("uuid") String uuid) {
        try {
            RentDTO rent = RentMapper.toRentDTO(getRentsUseCase.getRent(UUID.fromString(uuid)));
            return ResponseEntity.ok(getJSONFromRent(rent).toString());
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/account/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RentDTO>> rentsForAccount(@PathVariable("login") String login) {
        try {
            return ResponseEntity.ok(getRentsUseCase.getRentsForAccount(login).stream()
                    .map(RentMapper::toRentDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/vehicle/{vin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RentDTO>> rentsForVehicle(@PathVariable("vin") String vin) {
        try {
            return ResponseEntity.ok(getRentsUseCase.getRentsForVehicle(vin).stream()
                    .map(RentMapper::toRentDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @PutMapping(value = "/rent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateRent(RentDTO rent) {
        updateRentUseCase.updateRent(RentMapper.toRent(rent));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/endRent/{uuid}")
    public ResponseEntity<Void> endRent(@PathVariable("uuid") String uuid) {
        RentDTO rent = RentMapper.toRentDTO(getRentsUseCase.getRent(UUID.fromString(uuid)));

        if (rent.getEndDate() == null) {
            endRentUseCase.endRent(RentMapper.toRent(rent));
        } else {
            endRentUseCase.endRent(RentMapper.toRent(rent), rent.getEndDate());
        }
        return ResponseEntity.ok().build();
    }

    private JSONObject getJSONFromRent(RentDTO rent) {
        //wrapping values with JSONObject.wrap to avoid NPE and properly save null values
        return new JSONObject(Map.of(
                "uuid", JSONObject.wrap(rent.getUuid()),
                "account", JSONObject.wrap(rent.getAccountDTO().getLogin()),
                "vehicle", JSONObject.wrap(rent.getVehicleDTO().getVin()),
                "startDate", JSONObject.wrap(rent.getStartDate()),
                "endDate", JSONObject.wrap(rent.getEndDate())
        ));
    }

    private UUID createNotStartedRentAndGetUUID(RentDTO rent) {
        return createRentUseCase.createRent(
                AccountMapper.toAccount(rent.getAccountDTO()),
                VehicleMapper.toVehicle(rent.getVehicleDTO())
        ).getUuid();
    }

    private UUID createStartedRentAndGetUUID(RentDTO rent) {
        return createRentUseCase.createRent(
                AccountMapper.toAccount(rent.getAccountDTO()),
                VehicleMapper.toVehicle(rent.getVehicleDTO()), rent.getStartDate()
        ).getUuid();
    }

    private UUID createFinishedRentAndGetUUID(RentDTO rent) {
        return createRentUseCase.createRent(
                AccountMapper.toAccount(rent.getAccountDTO()),
                VehicleMapper.toVehicle(rent.getVehicleDTO()),
                rent.getStartDate(), rent.getEndDate()
        ).getUuid();
    }

//    private RentDTO getRentFromJSON(JsonObject rentJSON) {
//        AccountDTO account = AccountMapper.toAccountDTO(getAccountsUseCase.getAccount(rentJSON.getString("account")));
//        VehicleDTO vehicle = VehicleMapper.toVehicleDTO(getVehiclesUseCase.getVehicle(rentJSON.getString("vehicle")));
//
//        if (rentJSON.containsKey("uuid")) {
//            LocalDateTime startDate = rentJSON.isNull("startDate") ? null : LocalDateTime.parse(rentJSON.getString("startDate"));
//            LocalDateTime endDate = rentJSON.isNull("endDate") ? null : LocalDateTime.parse(rentJSON.getString("endDate"));
//            return new RentDTO(rentJSON.getString("uuid"), account, vehicle, startDate, endDate);
//        }
//        return new RentDTO(account, vehicle, LocalDateTime.parse(rentJSON.getString("startDate")), LocalDateTime.parse(rentJSON.getString("endDate")));
//    }
}