package p.lodz.tul.RestAdapter.endpoints;

import p.lodz.tul.ApplicationPorts.service.CreateAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.GetAccountsUseCase;
import p.lodz.tul.ApplicationPorts.service.RemoveAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.UpdateAccountUseCase;
import p.lodz.tul.RestAdapter.dto.AccountDTO;
import p.lodz.tul.RestAdapter.mappers.AccountMapper;
import p.lodz.tul.RestAdapter.mappers.LevelOfAccessMapper;
import p.lodz.tul.RestAdapter.util.ClientAccountDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.istack.NotNull;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Log
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final GetAccountsUseCase getAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final RemoveAccountUseCase removeAccountUseCase;
    private final ClientAccountDeserializer jsonDeserializer;

    @Autowired
    public AccountController(GetAccountsUseCase getAccountsUseCase, CreateAccountUseCase createAccountUseCase, UpdateAccountUseCase updateAccountUseCase, RemoveAccountUseCase removeAccountUseCase, ClientAccountDeserializer jsonDeserializer) {
        this.getAccountsUseCase = getAccountsUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
        this.removeAccountUseCase = removeAccountUseCase;
        this.jsonDeserializer = jsonDeserializer;
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerClient(@NotNull JSONObject accountDtoJson) {
        AccountDTO accountDTO;

        try {
            accountDTO = jsonDeserializer.fromJson(accountDtoJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).build();
        }

        if (areAccountPropertiesNull(accountDTO)) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        createAccountUseCase.createAccount(
                accountDTO.getEmail(),
                accountDTO.getLogin(),
                accountDTO.getPassword(),
                LevelOfAccessMapper.toLevelOfAccess(accountDTO.getLevelOfAccess())
        );
        return ResponseEntity.status(CREATED).build();
    }

    private boolean areAccountPropertiesNull(AccountDTO accountDTO) {
        return (accountDTO.getEmail() == null) ||
                (accountDTO.getLogin() == null) ||
                (accountDTO.getPassword() == null) ||
                (accountDTO.getLevelOfAccess() == null);
    }

    @GetMapping(value = "{accountLogin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> accountByLogin(@PathVariable("accountLogin") String accountLogin) {
        try {
            return ResponseEntity.ok(AccountMapper.toAccountDTO(getAccountsUseCase.getAccount(accountLogin)));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allAccounts() {
        try {
            return ResponseEntity.ok(
                    getAccountsUseCase.getAllAccounts().stream()
                            .map(AccountMapper::toAccountDTO).collect(Collectors.toList()));

        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @DeleteMapping("{accountLogin}")
    public ResponseEntity<?> removeAccount(@PathVariable("accountLogin") String accountLogin) {
        try {
            removeAccountUseCase.removeAccount(accountLogin);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).build();
        }
    }

    @PutMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@NotNull JSONObject accountDtoJson) {
        AccountDTO accountDTO;

        try {
            accountDTO = jsonDeserializer.fromJson(accountDtoJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).build();
        }

        if (areAccountPropertiesNull(accountDTO)) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        updateAccountUseCase.updateAccount(AccountMapper.toAccount(accountDTO));
        return ResponseEntity.ok().build();
    }
}

