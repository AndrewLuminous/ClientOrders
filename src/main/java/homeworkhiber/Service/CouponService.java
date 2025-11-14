package homeworkhiber.Service;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Coupon;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService
{
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;
    public Coupon getById(Long id)
    {
        try(Session session = sessionFactory.openSession()) {
            return session.find(Coupon.class, id);
        }
    }
    public CouponService(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }
    public Coupon saveCoupon(Coupon coupon)
    {
        return transactionHelper.executeInTransaction(session ->
        {
            session.persist(coupon);
            return coupon;
        });
    }
    public Coupon updateCoupon(Coupon coupon)
    {
        return transactionHelper.executeInTransaction(session ->
        {
            session.merge(coupon);
            return coupon;
        });
    }
    public void enrollClientToCoupon(
            Long clientId,
            Long couponId
    )
    {
        transactionHelper.executeInTransaction(session ->
        {

            session.createNativeQuery("INSERT INTO coupons_client (client_id, coupon_id) VALUES (:clientId, :couponId)", Void.class)
                    .setParameter("clientId", clientId)
                    .setParameter("couponId", couponId).executeUpdate();
        });
    }
    public List<Coupon> getAllCoupons() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT o FROM Coupon o ",
                            Coupon.class)
                    .list();
        }
        }
}