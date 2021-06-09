package p.tul.domainmodel.exceptions;

public class AccountException extends Exception {
    
    public static final String ACCOUNT_NOT_FOUND = "Account with given login was not found.";
    public static final String NULL_VALUE = "Provided account is null.";
    public static final String ACCOUNT_EXISTS = "Provided account already exists.";
    
    public AccountException(String message) {
        super(message);
    }
}
