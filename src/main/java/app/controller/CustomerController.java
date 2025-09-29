package app.controller;

import app.domain.Customer;
import app.service.CustomerService;

import java.util.List;

public class CustomerController {

    private final CustomerService service = new CustomerService();

    public Customer save (String name) {
        Customer customer = new Customer(name);
        return service.save(customer);
    }

    public List<Customer> getAll() {
        return service.getAllActiveCustomer();
    }

    public Customer getById(String id) {
        long numericId = Long.parseLong(id);
        return service.getActiveCustomerById(numericId);
    }

    public void update(String id, String newName) {
        long numericId = Long.parseLong(id);
        service.update(numericId, newName);
    }

    public void deleteById(String id) {
        long numericId = Long.parseLong(id);
        service.deleteById(numericId);
    }

    public void deleteByName(String name) {
        service.deleteByName(name);
    }

    public void restoreById(String id) {
        long numericId = Long.parseLong(id);
        service.restoreById(numericId);
    }

    public int getCustomersNumber() {
        return service.getActiveCustomerNumber();
    }

    public double getCustomersCartTotalCost(String customerId) {
        long numericCustomerId = Long.parseLong(customerId);
        return service.getCustomerCartTotalCost(numericCustomerId);
    }

    public double getCustomersCartAveragePrice(String customerId) {
        long numericCustomerId = Long.parseLong(customerId);
        return service.getCustomerCartAveragePrice(numericCustomerId);
    }

    //    Добавить билет в корзину покупателя по их идентификаторам (если оба активны)
    public void addTicketToCustomersCart(String customerId, String ticketId) {
        long numericCustomerId = Long.parseLong(customerId);
        long numericTicketId = Long.parseLong(ticketId);
        service.addTicketToCustomersCart(numericCustomerId, numericTicketId);
    }
    //    Удалить билет из корзины покупателя по их идентификаторам
    public void removeTicketFromCustomersCart(String customerId, String ticketId) {
        long numericCustomerId = Long.parseLong(customerId);
        long numericTicketId = Long.parseLong(ticketId);
        service.removeTicketFromCustomersCart(numericCustomerId, numericTicketId);
    }
    //    Полностью очистить корзину покупателя по его идентификатору (если он активен)
    public void clearCustomerCart(String customerId) {
        long numericCustomerId = Long.parseLong(customerId);
        service.clearCustomersCart(numericCustomerId);
    }
}
