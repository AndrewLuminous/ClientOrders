package homeworkhiber.Service;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Order;
import homeworkhiber.Entity.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;
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
    public void updateClientWithProfile(Client client, Profile profile) {
        transactionHelper.executeInTransaction(session -> {
            Client managedClient = session.merge(client);
            Profile managedProfile = session.merge(profile);
            managedClient.setProfile(managedProfile);
            managedProfile.setClient(managedClient);
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
    public Client getById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Client client = session.createQuery(
                            "SELECT DISTINCT c FROM Client c " +
                                    "LEFT JOIN FETCH c.orders " +
                                    "LEFT JOIN FETCH c.profile " +
                                    "WHERE c.id = :id", Client.class)
                    .setParameter("id", id)
                    .uniqueResult();
            Hibernate.initialize(client.getCoupons());
            return client;
        }
    }
    public Client getByIdGraph(Long id) {
        try(Session session = sessionFactory.openSession()) {

            EntityGraph<Client> entityGraph = session.createEntityGraph(Client.class);
            entityGraph.addAttributeNodes("profile", "orders");
            Client client = session.createQuery("SELECT DISTINCT c FROM Client c " +
                            "WHERE c.id = :id", Client.class)
                    .setHint("jakarta.persistence.loadgraph", entityGraph)
                    .setParameter("id", id).getSingleResult();
            Hibernate.initialize(client.getCoupons());
            return client;
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
            List<Client> clients = session.createQuery(
                            "SELECT DISTINCT o FROM Client o " +
                                    "LEFT JOIN FETCH o.profile "
                            + "LEFT JOIN FETCH o.orders ",
                            Client.class)
                    .list();
            clients.forEach(client -> {
                Hibernate.initialize(client.getOrders());
            });
            return clients;
        }
    }
}