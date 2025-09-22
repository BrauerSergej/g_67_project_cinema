package app.repository;

import app.domain.Ticket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TicketRepository {

    // Имитация базы данных
    private final List<Ticket> database = new ArrayList<>();

    // Поле, которое учитывает, какой сейчас максимальный ID тикета в базе данных
    private long maxId;

    // Метод, который сохраняет новый тикет в базе данных (Create)
    public Ticket save(Ticket ticket) {
        ticket.setId(++maxId);
        database.add(ticket);
        return ticket;
    }

    // Метод, который возвращает все тикеты из базы данных(Read)
    public List<Ticket> findAll() {
        return database;
    }

    // Метод, который возвращает один конкретный тикет по идентификатору (Read)
    public Ticket findById(Long id) {
        for (Ticket ticket : database) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    // Метод, который изменяет цену существующего тикета в базе данных (Update)
    public void update(Long id, double newPrice) {
        for (Ticket ticket : database) {
            if (ticket.getId().equals(id)) {
                ticket.setPrice(newPrice);
                break;
            }
        }
    }

    // Метод, который удаляет тикет из базы данных (Delete)
    private void deleteById(Long id) {
        Iterator<Ticket> iterator = database.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}