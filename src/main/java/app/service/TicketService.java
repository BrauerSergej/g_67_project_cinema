package app.service;

import app.domain.Genre;
import app.domain.Ticket;
import app.execeptions.TicketNotFoundException;
import app.execeptions.TicketSaveException;
import app.execeptions.TicketSaveExecepiton;
import app.execeptions.TicketUpdateException;
import app.repository.TicketRepository;

import java.text.ParseException;
import java.util.List;

public class TicketService {

    private static TicketService instance;
    // прописываем чтобы у repository вызывать методы
    private final TicketRepository repository = new TicketRepository();

    private TicketService() {

    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    //Сохранить тикет в базе данных (при сохранении тикет автоматически считается активным).
    public Ticket save(Ticket ticket) {


        if (ticket == null) {
            throw new TicketSaveException("Билет не может быть null");
        }

        String title = ticket.getTitleMovie();
        if (title == null || title.trim().isEmpty()) {
            throw new TicketSaveExecepiton("Наименование тикета не должно быть пустым");
        }

        if (ticket.getPrice() < 0) {
            throw new TicketSaveExecepiton("Цена билета не должна быть отрицательной");
        }

        if (ticket.getGenre() == null) {
            throw new TicketSaveException("Жанр билета не может быть null");
        }

        ticket.setActive(true);
        return repository.save(ticket);



    }

    //Вернуть все тикеты из базы данных (активные).
    public List<Ticket> getAllActiveTickets() {
        return repository.findAll()
                .stream()
                .filter(Ticket::isActive)
                .toList();
    }

    //Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Ticket getActiveTicketById(Long id) {
        Ticket ticket = repository.findById(id);
        if (ticket == null || !ticket.isActive()) {
            throw new TicketNotFoundException(id);
        }

        return ticket;
    }

    //Изменить один тикет в базе данных по его идентификатору.
    public void update(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new TicketUpdateException("Цена билета не должна быть отрицательной");
        }
        repository.update(id, newPrice);
    }

    //Удалить фильм из базы данных по его идентификатору.
    public void deleteById(Long id) {
        Ticket ticket = getActiveTicketById(id);
        ticket.setActive(false);
    }

    //Удалить тикет из базы данных по его наименованию.
    public void deleteByTitle(String title) {
        getAllActiveTickets()
                .stream()
                .filter(x -> x.getTitleMovie().equals(title))
                .forEach(x -> x.setActive(false));
    }

    //Восстановить удалённый тикет в базе данных по его идентификатору.
    public void restoreById(Long id) {
        Ticket ticket = repository.findById(id);

        if (ticket == null) {
            throw new TicketNotFoundException(id);
        }

        ticket.setActive(true);
    }

    //Вернуть общее количество продуктов в базе данных (активных).
    public int getActiveTicketsCount() {
        return getAllActiveTickets().size();
    }

    //Вернуть суммарную стоимость всех тикетов в базе данных (активных).
    public double getActiveTicketsTotalCost() {
        return getAllActiveTickets()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public double getActiveTicketsAveragePrice() {
        return getAllActiveTickets()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .average()
                .orElse(0.0);
    }

    public List<Ticket> getActiveTicketsByGenre(Genre genre) {
        return getAllActiveTickets()
                .stream()
                .filter(x -> x.getGenre().equals(genre))
                .toList();
    }
}
