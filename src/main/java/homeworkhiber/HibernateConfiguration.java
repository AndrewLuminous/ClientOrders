package homeworkhiber;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Coupon;
import homeworkhiber.Entity.Order;
import homeworkhiber.Entity.Profile;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Coupon.class)
                .addAnnotatedClass(Profile.class)
                .addAnnotatedClass(Order.class)
                .addPackage("homeworkhiber")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return configuration.buildSessionFactory();
    }
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}