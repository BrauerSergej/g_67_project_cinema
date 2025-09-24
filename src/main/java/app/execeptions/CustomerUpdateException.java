package app.execeptions;

public class CustomerUpdateException extends RuntimeException {
    public CustomerUpdateException(String message) {
        super(message);
    }
}
