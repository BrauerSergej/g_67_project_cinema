package app.controller;

import app.domain.Genre;
import app.domain.Ticket;
import app.service.TicketService;

import java.util.List;

public class TicketController {

    public final TicketService service = TicketService.getInstance();

    //    Сохранить билет в базе данных (при сохранении билет автоматически считается активным).
    public Ticket save(String title, String genre, String price) {
        Genre genre1 = Genre.valueOf(genre.toUpperCase());
        double numericPrice = Double.parseDouble(price);
        Ticket ticket = new Ticket(title, genre1, numericPrice);
        return service.save(ticket);
    }

    //    Вернуть все билеты из базы данных (активные).
    public List<Ticket> getAll() {
        return service.getAllActiveTickets();
    }

    //    Вернуть один билет из базы данных по его идентификатору (если он активен).
    public Ticket getById(String id) {
        long numericId = Long.parseLong(id);
        return service.getActiveTicketById(numericId);
    }
    //    Изменить один билет в базе данных по его идентификатору.
    public void update(String id, String newPrice) {
        long numericId = Long.parseLong(id);
        double numericNewPrice = Double.parseDouble(newPrice);
        service.update(numericId, numericNewPrice);
    }
    //    Удалить билет из базы данных по его идентификатору.
    public void deleteById(String id) {
        long numericId = Long.parseLong(id);
        service.deleteById(numericId);
    }
    //    Удалить билет из базы данных по его наименованию.
    public void deleteByTitle(String title) {
        service.deleteByTitle(title);
    }
    //    Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(String id) {
        long numericId = Long.parseLong(id);
        service.restoreById(numericId);
    }
    //    Вернуть общее количество билетов в базе данных (активных).
    public int getTicketsCount() {
        return service.getActiveTicketsCount();
    }
    //    Вернуть суммарную стоимость всех билетов в базе данных (активных).
    public double getTicketsTotalCost() {
        return service.getActiveTicketsTotalCost();
    }
    //    Вернуть среднюю стоимость билета в базе данных (из активных).
    public double getTicketsAveragePrice() {
        return service.getActiveTicketsAveragePrice();
    }
    // Вернуть все фильмы по жанру в базе данных (активных)
    public List<Ticket> getAllActiveTicketsByGenres(String genre){
        Genre genre1 = Genre.valueOf(genre.toUpperCase());
        return service.getActiveTicketsByGenre(genre1);
    }
}

