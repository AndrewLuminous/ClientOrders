package homeworkhiber;

import homeworkhiber.CliView.MainMenu;
import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Coupon;
import homeworkhiber.Entity.Order;
import homeworkhiber.Entity.Profile;
import homeworkhiber.Service.ClientService;
import homeworkhiber.Service.CouponService;
import homeworkhiber.Service.OrderService;
import homeworkhiber.Service.ProfileService;
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
        OrderService orderService = context.getBean(OrderService.class);
        ProfileService profileService = context.getBean(ProfileService.class);
        CouponService couponService = context.getBean(CouponService.class);
        MainMenu mainMenu = context.getBean(MainMenu.class);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Client client1 = new Client("Богдан", "Богдан@gmail.com", LocalDateTime.now());
        Client client2 = new Client("Василий", "Василий@gmail.com", LocalDateTime.now());
        Client client3 = new Client("Григорий", "Григорийg@mail.com", LocalDateTime.now());
        Client client4 = new Client("Павел", "Павел@gmail.com", LocalDateTime.now());
        Client client5 = new Client("Иван", "Иван@gmail.com", LocalDateTime.now());
        Client client6 = new Client("Петр", "Петр@gmail.com", LocalDateTime.now());
        Client client7 = new Client("Александр", "Александр@gmail.com", LocalDateTime.now());
        Client client8 = new Client("Евгений", "Евгений@gmail.com", LocalDateTime.now());
        Client client9 = new Client("София", "София@gmail.com", LocalDateTime.now());


        Client SavedClient1 = clientService.saveClient(client1);
        Client SavedClient2 = clientService.saveClient(client2);
        Client SavedClient3 = clientService.saveClient(client3);
        Client SavedClient4 = clientService.saveClient(client4);
        Client SavedClient5 = clientService.saveClient(client5);
        Client SavedClient6 = clientService.saveClient(client6);
        Client SavedClient7 = clientService.saveClient(client7);
        Client SavedClient8 = clientService.saveClient(client8);
        Client SavedClient9 = clientService.saveClient(client9);

        Profile profile1 = new Profile("+7 999 888 77 66","Первомайская 12",SavedClient1);
        Profile profile2 = new Profile("+7 888 999 66 77","Кривосадово 4",SavedClient2);
        Profile profile3 = new Profile("+7 777 777 77 77","Структурное 7",SavedClient3);
        Profile profile4 = new Profile("+7 999 999 99 99","Изящное 1",SavedClient4);
        Profile profile5 = new Profile("+7 888 888 88 88","Превосходное 10",SavedClient5);
        Profile profile6 = new Profile("+7 666 666 66 66","Топорово 51",SavedClient6);
        Profile profile7 = new Profile("+7 666 777 77 77","Метрово 12",SavedClient7);
        Profile profile8 = new Profile("+7 666 777 77 88","Кластерово 61",SavedClient8);
        Profile profile9 = new Profile("+7 666 888 88 88","Тыквенное 6",SavedClient9);

        SavedClient1.setProfile(profile1);
        SavedClient2.setProfile(profile2);
        SavedClient3.setProfile(profile3);
        SavedClient4.setProfile(profile4);
        SavedClient5.setProfile(profile5);
        SavedClient6.setProfile(profile6);
        SavedClient7.setProfile(profile7);
        SavedClient8.setProfile(profile8);
        SavedClient9.setProfile(profile9);
        profileService.saveProfile(profile1);
        profileService.saveProfile(profile2);
        profileService.saveProfile(profile3);
        profileService.saveProfile(profile4);
        profileService.saveProfile(profile5);
        profileService.saveProfile(profile6);
        profileService.saveProfile(profile7);
        profileService.saveProfile(profile8);
        profileService.saveProfile(profile9);

        Order order1 = new Order(100, SavedClient1, LocalDateTime.now());
        Order order2 = new Order(200, SavedClient2, LocalDateTime.now());
        Order order3 = new Order(300, SavedClient3, LocalDateTime.now());
        Order order4 = new Order(400, SavedClient4, LocalDateTime.now());
        Order order5 = new Order(500, SavedClient5, LocalDateTime.now());
        Order order6 = new Order(600, SavedClient6, LocalDateTime.now());
        Order order7 = new Order(700, SavedClient7, LocalDateTime.now());
        Order order8 = new Order(800, SavedClient8, LocalDateTime.now());
        Order order9 = new Order(900, SavedClient9, LocalDateTime.now());

        Coupon coupon1 = new Coupon(1421461L,20,SavedClient1);
        Coupon coupon2 = new Coupon(5214213L,30,SavedClient2);
        Coupon coupon3 = new Coupon(51251L,40,SavedClient3);
        Coupon coupon4 = new Coupon(61234L,50,SavedClient4);
        Coupon coupon5 = new Coupon(6123412421L,60,SavedClient5);
        Coupon coupon6 = new Coupon(12321321L,70,SavedClient6);
        Coupon coupon7 = new Coupon(6123213L,80,SavedClient7);
        Coupon coupon8 = new Coupon(12325151L,10,SavedClient8);
        Coupon coupon9 = new Coupon(75123412L,5,SavedClient9);

        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        orderService.saveOrder(order3);
        orderService.saveOrder(order4);
        orderService.saveOrder(order5);
        orderService.saveOrder(order6);
        orderService.saveOrder(order7);
        orderService.saveOrder(order8);
        orderService.saveOrder(order9);

        couponService.saveCoupon(coupon1);
        couponService.saveCoupon(coupon2);
        couponService.saveCoupon(coupon3);
        couponService.saveCoupon(coupon4);
        couponService.saveCoupon(coupon5);
        couponService.saveCoupon(coupon6);
        couponService.saveCoupon(coupon7);
        couponService.saveCoupon(coupon8);
        couponService.saveCoupon(coupon9);

        couponService.enrollClientToCoupon(coupon1.getId(), SavedClient1.getId());
        couponService.enrollClientToCoupon(coupon2.getId(), SavedClient2.getId());
        couponService.enrollClientToCoupon(coupon3.getId(), SavedClient3.getId());
        couponService.enrollClientToCoupon(coupon4.getId(), SavedClient4.getId());
        couponService.enrollClientToCoupon(coupon5.getId(), SavedClient5.getId());
        couponService.enrollClientToCoupon(coupon6.getId(), SavedClient6.getId());
        couponService.enrollClientToCoupon(coupon7.getId(), SavedClient7.getId());
        couponService.enrollClientToCoupon(coupon8.getId(), SavedClient8.getId());
        couponService.enrollClientToCoupon(coupon9.getId(), SavedClient9.getId());
        session.getTransaction().commit();
        mainMenu.show();
        session.close();
    }
}

