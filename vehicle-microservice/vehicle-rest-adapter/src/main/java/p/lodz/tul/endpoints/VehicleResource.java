package p.lodz.tul.endpoints;

import java.util.List;
import java.util.stream.Collectors;
import p.lodz.tul.dto.VehicleDTO;
import p.lodz.tul.mappers.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import p.lodz.tul.service.CreateVehicleUseCase;
import p.lodz.tul.service.GetVehiclesUseCase;
import p.lodz.tul.service.RemoveVehicleUseCase;
import p.lodz.tul.service.UpdateVehicleUseCase;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleResource {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final RemoveVehicleUseCase removeVehicleUseCase;
    private final GetVehiclesUseCase getVehiclesUseCase;

    @Autowired
    public VehicleResource(CreateVehicleUseCase createVehicleUseCase, 
                           UpdateVehicleUseCase updateVehicleUseCase, 
                           RemoveVehicleUseCase removeVehicleUseCase, 
                           GetVehiclesUseCase getVehiclesUseCase) {
        this.createVehicleUseCase = createVehicleUseCase;
        this.updateVehicleUseCase = updateVehicleUseCase;
        this.removeVehicleUseCase = removeVehicleUseCase;
        this.getVehiclesUseCase = getVehiclesUseCase;
    }

    @PostMapping(path = "/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        if (areVehiclePropertiesNull(vehicleDTO)) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        createVehicleUseCase.createVehicle(
                vehicleDTO.getManufacturerName(),
                vehicleDTO.getModelName(),
                vehicleDTO.getBaseLoanPrice(),
                vehicleDTO.getVin(),
                vehicleDTO.getLicencePlate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private boolean areVehiclePropertiesNull(@RequestBody VehicleDTO vehicleDTO) {
        return (vehicleDTO.getVin() == null) ||
                (vehicleDTO.getLicencePlate() == null) ||
                (vehicleDTO.getManufacturerName() == null) ||
                (vehicleDTO.getModelName() == null);
    }

    @GetMapping(path = "{vehicleVin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDTO> vehicleByVin(@PathVariable("vehicleVin") String vehicleVin) {
        try {
            return ResponseEntity.ok().body(VehicleMapper.toVehicleDTO(getVehiclesUseCase.getVehicle(vehicleVin)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> allVehicles() {
        try {
            return ResponseEntity.ok().body(getVehiclesUseCase.getAllVehicles().stream()
                    .map(VehicleMapper::toVehicleDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(path = "{vehicleVin}")
    public ResponseEntity<Void> removeVehicle(@PathVariable("vehicleVin") String vehicleVin) {
        try {
            removeVehicleUseCase.removeVehicle(vehicleVin);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(path = "/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@RequestBody VehicleDTO vehicle) {
        updateVehicleUseCase.updateVehicle(VehicleMapper.toVehicle(vehicle));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
