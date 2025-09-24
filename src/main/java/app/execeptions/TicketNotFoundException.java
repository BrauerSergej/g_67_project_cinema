package app.execeptions;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long id) {
        super(String.format("Билет с ID %d не найден", id));
    }
}
