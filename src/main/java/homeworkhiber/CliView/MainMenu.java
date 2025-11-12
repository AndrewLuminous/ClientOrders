package homeworkhiber.CliView;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Coupon;
import homeworkhiber.Entity.Order;
import homeworkhiber.Entity.Profile;
import homeworkhiber.Service.ClientService;
import homeworkhiber.Service.CouponService;
import homeworkhiber.Service.ProfileService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class MainMenu
{
    private final Scanner scanner;
    private final ClientCli clientCli;
    private final ClientService clientService;
    private final ProfileCli profileCli;
    private final ProfileService profileService;
    private final SessionFactory sessionFactory;
    private final OrderCli orderCli;
    private final CouponService couponService;

    public MainMenu(Scanner scanner, ClientCli clientCli, ClientService clientService, ProfileCli profileCli, ProfileService profileService, SessionFactory sessionFactory, OrderCli orderCli, CouponService couponService) {
        this.scanner = scanner;
        this.clientCli = clientCli;
        this.clientService = clientService;
        this.profileCli = profileCli;
        this.profileService = profileService;
        this.sessionFactory = sessionFactory;
        this.orderCli = orderCli;
        this.couponService = couponService;
    }

    public void show() {
        while (true)
        {
            System.out.println("1. Добавить Клиента");
            System.out.println("2. Удалить клиента");
            System.out.println("3. Редактировать профиль");
            System.out.println("4. Добавить заказ");
            System.out.println("5. Выбрать купоны");
            System.out.println("6. Найти заказы");
            System.out.println("7. Выход");
            switch (makeChoice())
            {
                case 1 -> createClientWithProfile();
                case 2 -> deleteClientWithProfile();
                case 3 -> updateClientOrProfile();
                case 4 -> addOrder();
                case 5 -> chooseCoupon();
                case 6 -> findOrder();
            }
        }
    }

    private void findOrder()
    {
        try {
            System.out.println("Найти заказ:");
            System.out.println("Вывести по номеру");
            System.out.println("");
            System.out.println("");
        }
        catch (InputMismatchException e)
        {
            System.out.println("Невалидные данные");
        }
    }

    private void chooseCoupon()
    {
        try {
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name: %s, id: %d \t", client.getName(),client.getId());
                System.out.println("\nВыберите клиента по id");
                Client clint = clientService.getById(scanner.nextLong());
                couponService.getAllCoupons().forEach(coupon -> {
                    System.out.printf("number: %d discount: %d %%", coupon.getCode(),coupon.getDiscount());
                });
                System.out.println("\nВыберите купон по id");
                Coupon coupon = couponService.getById(scanner.nextLong());
                couponService.enrollClientToCoupon(client.getId(), coupon.getId());
                System.out.println("Успешно выбран купон!");
            });
        }
        catch (Exception e)
        {
            System.out.println("Пошло что-то не так " + e.getMessage());
            return;
        }
    }

    private void addOrder()
    {
        try{
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name: %s, id: %d \t", client.getName(),client.getId());
            });
            System.out.println("\nВыберите клиента по id");
            Client client = clientService.getById(scanner.nextLong());
            Order order = orderCli.createOrder(client);
            client.getOrders().add(order);
            clientService.updateClient(client);
            System.out.println("Товар успешно сохранен");
        }
        catch (Exception e)
        {
            System.out.println("Пошло что-то не так" + e.getMessage());
            return;
        }
    }

    private void updateClientOrProfile()
    {
        try
        {
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name : %s id : %d \t", client.getName(), client.getId());
            });
            System.out.println("Выберите клиента по id");
            Client client = clientService.getById(scanner.nextLong());
            Profile profile = client.getProfile();
            boolean bool = true;
            while (bool) {
                System.out.println("Что хотите поменять?");
                System.out.println("1. Имя");
                System.out.println("2. Емейл");
                System.out.println("3. Телефон");
                System.out.println("4. Адрес");
                System.out.println("5. Выход");
                switch (scanner.nextInt())
                {
                    case 1 -> {
                        System.out.println("Введите новое имя");
                        client.setName(scanner.nextLine());
                    }
                    case 2 -> {
                        System.out.println("Введите новый емейл");
                        client.setEmail(scanner.nextLine());
                    }
                    case 3 -> {
                        System.out.println("Введите новый телефон");
                        profile.setPhoneNumber(scanner.nextLine());
                    }
                    case 4 -> {
                        System.out.println("Введите новый адрес");
                        profile.setAddress(scanner.nextLine());
                    }
                    case 5 -> {
                        try(Session session = sessionFactory.openSession()) {
                            Transaction transaction = session.getTransaction();
                            transaction.begin();
                            Client savedClient = clientService.updateClient(client);
                            Profile savedProfile = profileService.saveProfile(profile);
                            transaction.commit();
                            System.out.println("Успешно сохранено!");
                            bool = false;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("\nТакого не найдено " + e.getMessage());
            return;
        }
        }


    private void deleteClientWithProfile()
    {
        try{
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name: %s id : %d \n", client.getName(), client.getId());
            });
            System.out.println("\nВыберите клиента по id");
            Client deleteClient = clientService.getById(scanner.nextLong());
        }
        catch (Exception e)
        {
            System.out.println("Что-то не так + " + e.getMessage());
        }

    }

    private void createClientWithProfile()
    {
        try {
            Client client = clientCli.createCLient();
            Profile profile = profileCli.createProfile(client);
            client.setProfile(profile);
            profile.setClient(client);
            clientService.saveClient(client);
            profileService.saveProfile(profile);
            System.out.println("Успешно создан клиент и профиль!");
        }
        catch (Exception e)
        {
            System.out.println("Ошибка : " + e.getMessage());
        }
    }

    private int makeChoice()
    {
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please enter a valid number");
        }
        return -1;
    }
}
