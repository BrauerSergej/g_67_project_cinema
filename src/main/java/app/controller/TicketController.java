package app.controller;

import app.domain.Genre;
import app.domain.Ticket;
import app.service.TicketService;

import java.util.List;

public class TicketController {

    public final TicketService service = TicketService.getInstance();

    //    Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
    public Ticket save(String title, String price, String genre) {
        Genre genre1 = Genre.valueOf(genre.toUpperCase());
        double numericPrice = Double.parseDouble(price);
        Ticket ticket = new Ticket(title, numericPrice, genre1);
        return service.save(ticket);
    }

    //    Вернуть все продукты из базы данных (активные).
    public List<Ticket> getAll() {
        return service.getAllActiveTickets();
    }

    //    Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Ticket getById(String id) {
        long numericId = Long.parseLong(id);
        return service.getActiveTicketById(numericId);
    }
    //    Изменить один продукт в базе данных по его идентификатору.
    public void update(String id, String newPrice) {
        long numericId = Long.parseLong(id);
        double numericNewPrice = Double.parseDouble(newPrice);
        service.update(numericId, numericNewPrice);
    }
    //    Удалить продукт из базы данных по его идентификатору.
    public void deleteByIde(String id) {
        long numericId = Long.parseLong(id);
        service.deleteById(numericId);
    }
    //    Удалить продукт из базы данных по его наименованию.
    public void deleteByTitle(String title) {
        service.deleteByTitle(title);
    }
    //    Восстановить удалённый продукт в базе данных по его идентификатору.
    public void restoreById(String id) {
        long numericId = Long.parseLong(id);
        service.restoreById(numericId);
    }
    //    Вернуть общее количество продуктов в базе данных (активных).
    public int getTicketsCount() {
        return service.getActiveTicketsCount();
    }
    //    Вернуть суммарную стоимость всех продуктов в базе данных (активных).
    public double getProductsTotalCost() {
        return service.getActiveTicketsTotalCost();
    }
    //    Вернуть среднюю стоимость продукта в базе данных (из активных).
    public double getTicketsAveragePrice() {
        return service.getActiveTicketsAveragePrice();
    }

    public List<Ticket> getAllActiveTicketsByGenres(String genre){
        Genre genre1 = Genre.valueOf(genre.toUpperCase());
        return service.getActiveTicketsByGenre(genre1);
    }
}

