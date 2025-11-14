package homeworkhiber.Service;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.OderStatus;
import homeworkhiber.Entity.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public OrderService(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Order saveOrder(Order order) {
        return transactionHelper.executeInTransaction(session ->
        {
            session.persist(order);
            return order;
        });
    }

    public Order updateOrder(Order order) {
        return transactionHelper.executeInTransaction(session ->
        {
            session.merge(order);
            return order;
        });
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.client c " +
                                    "LEFT JOIN FETCH c.profile " +
                                    "WHERE o.client.id = :clientId", Order.class)
                    .setParameter("clientId", clientId)
                    .list();
        }
    }

    public List<Order> getOrdersByAmountRange(int minAmount, int maxAmount) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.client c " +
                                    "LEFT JOIN FETCH c.profile " +
                                    "WHERE o.totalAmount BETWEEN :minAmount AND :maxAmount", Order.class)
                    .setParameter("minAmount", minAmount)
                    .setParameter("maxAmount", maxAmount)
                    .list();
        }
    }

    public List<Order> getOrdersByStatus(OderStatus status) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.client c " +
                                    "LEFT JOIN FETCH c.profile " +
                                    "WHERE o.status = :status", Order.class)
                    .setParameter("status", status)
                    .list();
        }
    }

    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.client c " +
                                    "LEFT JOIN FETCH c.profile " +
                                    "LEFT JOIN FETCH c.coupons",
                            Order.class)
                    .list();

        }
    }
}