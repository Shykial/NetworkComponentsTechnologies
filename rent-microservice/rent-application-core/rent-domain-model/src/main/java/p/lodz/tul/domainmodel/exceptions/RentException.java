package p.lodz.tul.domainmodel.exceptions;

public class RentException extends Exception {
    
    public static final String RENT_NOT_FOUND = "Rent with given UUID was not found.";
    public static final String NULL_VALUE = "Provided rent is null.";
    public static final String RENT_EXISTS = "Provided rent already exists.";
    
    public RentException(String message) {
        super(message);
    }
}
