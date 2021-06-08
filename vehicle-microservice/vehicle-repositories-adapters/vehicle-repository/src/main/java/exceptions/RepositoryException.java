package exceptions;

public class RepositoryException extends Exception {

    public static final String ENTITY_EXISTS = "Entity already exists in the repository.";
    public static final String NULL_VALUE = "Provided entity value is empty.";
    public static final String ENTITY_NOT_FOUND = "Entity was not found.";

    public RepositoryException(String message) {
        super(message);
    }
}
