package app.domain;

import java.util.List;
import java.util.Objects;

public class Ticket {
    private Long id;
    private String titleMovie;
    private double price;
    private boolean active;
    private Genre genre;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(price, ticket.price) == 0 && active == ticket.active && Objects.equals(id, ticket.id) && Objects.equals(titleMovie, ticket.titleMovie) && genre == ticket.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titleMovie, price, active, genre);
    }

    @Override
    public String toString() {
        return String.format("Ticket: id - %d, title - %s, price - %.2f, active - %b, genre - %s", id, titleMovie, price, active, genre);
    }
}
