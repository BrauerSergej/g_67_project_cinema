package app.service;

import app.domain.Customer;
import app.domain.Ticket;
import app.execeptions.CustomerNotFoundException;
import app.execeptions.CustomerSaveException;
import app.execeptions.CustomerUpdateException;
import app.execeptions.TicketNotFoundException;
import app.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository = new CustomerRepository();
    private final TicketService ticketService = TicketService.getInstance();
    //Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается активным).
    public Customer save(Customer customer) {
        if(customer == null) {
            throw new CustomerSaveException("Покупатель не может быть null");
        }

        String name = customer.getName();
        if(name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Имя покупателя не должно быть пустым");
        }
        customer.setActive(true);
        return repository.save(customer);
    }
    //Вернуть всех покупателей из базы данных (активных).
    public List<Customer> getAllActiveCustomer() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
    }
    //Вернуть одного покупателя из базы данных по его идентификатору (если он активен).
    public Customer getActiveCustomerById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }
    //Изменить одного покупателя в базе данных по его идентификатору.
    public void update(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new CustomerUpdateException("Имя покупателя не может быть пустым");
        }
        repository.update(id, newName);
    }
    //Удалить покупателя из базы данных по его идентификатору.
    public void deleteById(Long id) {
        Customer customer = getActiveCustomerById(id);
        customer.setActive(false);
    }
    //Удалить покупателя из базы данных по его имени.
    public void deleteByName(String name) {
        getAllActiveCustomer()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }

    //Восстановить удалённого покупателя в базе данных по его идентификатору.
    public void restoreById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        customer.setActive(true);
    }

    //Вернуть общее количество покупателей в базе данных (активных).
    public int getActiveCustomerNumber() {
        return getAllActiveCustomer().size();
    }

    //Вернуть стоимость корзины покупателя по его идентификатору (если он активен).
    public double getCustomerCartTotalCost(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Ticket::isActive)
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    //Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он активен)
    public double getCustomerCartAveragePrice(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Ticket::isActive)
                .mapToDouble(Ticket::getPrice)
                .average()
                .orElse(0.0);
    }

    //Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
    public void addTicketToCustomersCart(Long customerId, Long ticketId) {
        Customer customer = getActiveCustomerById(customerId);
        Ticket ticket = ticketService.getActiveTicketById(ticketId);
        customer.getCart().remove(ticket);
    }

    //Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
    public void removeTicketToCustomersCart(Long customerId, Long ticketId) {
        Customer customer = getActiveCustomerById(customerId);
        Ticket ticket= ticketService.getActiveTicketById(ticketId);
        customer.getCart().remove(ticket);
    }

    //Полностью очистить корзину покупателя по его идентификатору (если он активен)
    public void removeTicketFromCustomerCart(Long customerId) {
        Customer customer = getActiveCustomerById(customerId);
        customer.getCart().clear();
    }
}
