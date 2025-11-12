package homeworkhiber.Service;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ClientService
{
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;


    public ClientService(TransactionHelper transactionHelper, SessionFactory sessionFactory)
    {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }
    public Client saveClient(
            Client client
    ) {
        return transactionHelper.executeInTransaction(session ->
        {
            var newClient = new Client(client.getName(), client.getEmail(), client.getRegistrationDate());
            session.persist(newClient);
            return newClient;
        });
    }
    public void deleteClient(Long id)
    {
        transactionHelper.executeInTransaction(session ->
        {
            var deleteClient = session.find(Client.class, id);
            session.remove(deleteClient);
        });
    }
    public Client getById(Long id)
    {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Client.class, id);
        }
    }
    public Client updateClient(Client client)
    {
        return transactionHelper.executeInTransaction(session ->
        {
            session.merge(client);
            return client;
        });
    }
    public List<Client> getAllClients() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Client o " +
                                    "LEFT JOIN FETCH o.profile ",
                            Client.class)
                    .list();
        }
    }

}