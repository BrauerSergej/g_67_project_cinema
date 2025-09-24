package app.execeptions;

public class TicketSaveException extends RuntimeException {
    public TicketSaveException(String message) {
        super(message);
    }
}
