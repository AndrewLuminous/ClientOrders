package homeworkhiber.Service;

import homeworkhiber.Entity.Profile;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileService
{
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public ProfileService(TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Profile saveProfile(Profile profile)
    {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(profile);
            return profile;
        });
    }

}