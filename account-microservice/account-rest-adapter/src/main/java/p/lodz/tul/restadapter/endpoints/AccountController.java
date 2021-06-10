package p.lodz.tul.restadapter.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.tul.ApplicationPorts.service.CreateAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.GetAccountsUseCase;
import p.lodz.tul.ApplicationPorts.service.RemoveAccountUseCase;
import p.lodz.tul.ApplicationPorts.service.UpdateAccountUseCase;
import p.lodz.tul.restadapter.dto.AccountDTO;
import p.lodz.tul.restadapter.mappers.AccountMapper;
import p.lodz.tul.restadapter.mappers.LevelOfAccessMapper;
import p.lodz.tul.restadapter.mq.Publisher;
import p.lodz.tul.restadapter.util.ClientAccountDeserializer;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final GetAccountsUseCase getAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final RemoveAccountUseCase removeAccountUseCase;
    private final ClientAccountDeserializer jsonDeserializer;
    private final Publisher publisher;


    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerClient(@RequestBody AccountDTO accountDTO) {

        if (areAccountPropertiesNull(accountDTO)) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        createAccountUseCase.createAccount(
                accountDTO.getEmail(),
                accountDTO.getLogin(),
                accountDTO.getPassword(),
                LevelOfAccessMapper.toLevelOfAccess(accountDTO.getLevelOfAccess())
        );

        if (!publisher.send("create", accountDTO)) {
            return ResponseEntity.status(CONFLICT).build();
        }

        return ResponseEntity.status(CREATED).build();
    }

    private boolean areAccountPropertiesNull(AccountDTO accountDTO) {
        return (accountDTO.getEmail() == null) ||
                (accountDTO.getLogin() == null) ||
                (accountDTO.getPassword() == null) ||
                (accountDTO.getLevelOfAccess() == null);
    }

    @GetMapping(value = "{accountLogin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> accountByLogin(@PathVariable("accountLogin") String accountLogin) {
        try {
            return ResponseEntity.ok(AccountMapper.toAccountDTO(getAccountsUseCase.getAccount(accountLogin)));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDTO>> allAccounts() {
        try {
            return ResponseEntity.ok(
                    getAccountsUseCase.getAllAccounts().stream()
                            .map(AccountMapper::toAccountDTO).collect(Collectors.toList()));

        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @DeleteMapping("{accountLogin}")
    public ResponseEntity<Void> removeAccount(@PathVariable("accountLogin") String accountLogin) {
        try {
            removeAccountUseCase.removeAccount(accountLogin);
            if (!publisher.send("remove", accountLogin)) {
                return ResponseEntity.status(NOT_ACCEPTABLE).build();
            }
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).build();
        }
    }

    @PutMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAccount(@RequestBody AccountDTO accountDTO) {

        if (areAccountPropertiesNull(accountDTO)) {
            return ResponseEntity.status(EXPECTATION_FAILED).build();
        }

        updateAccountUseCase.updateAccount(AccountMapper.toAccount(accountDTO));

        if (!publisher.send("update", accountDTO)) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        }

        return ResponseEntity.noContent().build();
    }
}

