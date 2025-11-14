package homeworkhiber;

import homeworkhiber.CliView.MainMenu;
import homeworkhiber.Entity.Client;
import homeworkhiber.Service.ClientService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("homeworkhiber");
        SessionFactory sessionFactory = context.getBean("sessionFactory", SessionFactory.class);

        ClientService clientService = context.getBean(ClientService.class);
        MainMenu mainMenu = context.getBean(MainMenu.class);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Client client1 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client2 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client3 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client4 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client5 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client6 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client7 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client8 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        Client client9 = new Client("Андрей", "Андрей@mail.ru", LocalDateTime.now());
        clientService.saveClient(client1);
        clientService.saveClient(client2);
        clientService.saveClient(client3);
        clientService.saveClient(client4);
        clientService.saveClient(client5);
        clientService.saveClient(client6);
        clientService.saveClient(client7);
        clientService.saveClient(client8);
        clientService.saveClient(client9);
        session.getTransaction().commit();
        mainMenu.show();
        session.close();
    }
}

