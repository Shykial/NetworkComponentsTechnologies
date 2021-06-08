package p.lodz.tul.ApplicationServices;

public class ServiceException extends Exception{
    
    public static final String NULL_ARGUMENT = "One of the provided arguments is null.";
    public static final String RENT_ALREADY_ENDED = "Rent is already ended.";
    public static final String RENT_NOT_FOUND = "Rent that you are looking for was not found in the repository.";
    public static final String NOT_AUTHORIZED = "Provided account is not authorized to perform this operation.";
    public static final String NOT_ENOUGH_FUNDS = "Provided client does not have enough funds to rent this car.";
    public static final String NOT_AUTHENTICATED = "Provided account was not found in the repository.";
    public static final String RENT_NOT_ENDED = "Provided rent wasn't ended yet.";
    
    public ServiceException(String message) {
        super(message);
    }
}
