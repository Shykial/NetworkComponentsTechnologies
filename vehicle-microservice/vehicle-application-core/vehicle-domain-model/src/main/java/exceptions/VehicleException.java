package exceptions;

public class VehicleException extends Exception {

    public static final String VEHICLE_NOT_FOUND = "Vehicle with provided VIN was not found.";
    public static final String NULL_VALUE = "Provided vehicle is null.";
    public static final String VEHICLE_EXISTS = "Provided vehicle already exists.";

    public VehicleException(String message) {
        super(message);
    }
}
