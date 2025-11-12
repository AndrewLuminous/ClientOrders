package homeworkhiber.Service;

import homeworkhiber.Entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService
{
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public OrderService(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Order saveOrder(Order order)
    {
        return transactionHelper.executeInTransaction(session ->
        {
            session.persist(order);
            return order;
        });
    }

    public List<Order> getAllOrders() {
        try(Session session = sessionFactory.openSession()) {
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
