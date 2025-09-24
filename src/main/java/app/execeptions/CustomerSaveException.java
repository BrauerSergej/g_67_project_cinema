package app.execeptions;

public class CustomerSaveException extends RuntimeException {
    public CustomerSaveException(String message) {
        super(message);
    }
}
