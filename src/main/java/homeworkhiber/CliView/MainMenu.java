package homeworkhiber.CliView;

import homeworkhiber.Entity.*;
import homeworkhiber.Service.ClientService;
import homeworkhiber.Service.CouponService;
import homeworkhiber.Service.OrderService;
import homeworkhiber.Service.ProfileService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static homeworkhiber.Entity.OderStatus.*;

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
    private final OrderService orderService;

    public MainMenu(Scanner scanner, ClientCli clientCli, ClientService clientService, ProfileCli profileCli, ProfileService profileService, SessionFactory sessionFactory, OrderCli orderCli, CouponService couponService, OrderService orderService) {
        this.scanner = scanner;
        this.clientCli = clientCli;
        this.clientService = clientService;
        this.profileCli = profileCli;
        this.profileService = profileService;
        this.sessionFactory = sessionFactory;
        this.orderCli = orderCli;
        this.couponService = couponService;
        this.orderService = orderService;
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
            System.out.println("7. Оплатить/отменить покупку");
            System.out.println("7. Выход");
            switch (makeChoice())
            {
                case 1 -> createClientWithProfile();
                case 2 -> deleteClientWithProfile();
                case 3 -> updateClientOrProfile();
                case 4 -> addOrder();
                case 5 -> chooseCoupon();
                case 6 -> findOrder();
                case 7 -> buyOrCancel();
                case 8 -> {return;}
            }
        }
    }

    private void buyOrCancel()
    {
        try{
            System.out.println("Выберете клиента по id");
            clientService.getAllClients();
            Client client = clientService.getById(scanner.nextLong());
            List<Order> orders = client.getOrders();
            System.out.println("Выберите заказ для продолжения работы");
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                System.out.printf("%d. Заказ ID:%d, Сумма:%d руб.%n",
                        i + 1, order.getId(), order.getTotalAmount());
            }
            Order orderChosen = orders.get(scanner.nextInt() - 1);
            System.out.printf("Выбран заказ с %d номером и суммой %d", orderChosen.getId(), orderChosen.getTotalAmount());
            System.out.println("\n1. Оплатить заказ");
            System.out.println("2. Отменить заказ");

            switch (makeChoice())
            {
                case 1 -> {
                    System.out.println("Применить купон? 1 - да, 2 - нет");
                    switch (makeChoice())
                    {
                        case 1 -> {
                            Coupon coupon = client.getCoupons().getFirst();
                            int totalAmountWithDiscount = orderChosen.getTotalAmount() - (orderChosen.getTotalAmount() * coupon.getDiscount() / 100);
                            System.out.printf("Клиент %s оплатит %d с" +
                                    " помощью купона с номером %d",client.getName(),totalAmountWithDiscount,coupon.getDiscount());
                            orderChosen.setStatus(VERIFIED);
                            orderService.updateOrder(orderChosen);
                            System.out.println("Ваш заказ был успешно оплачен");
                        }
                        case 2 -> {
                            System.out.printf("Клиент %s оплатит %d", client.getName(), orderChosen.getTotalAmount());
                            orderService.updateOrder(orderChosen);
                            System.out.println("Ваш заказ был успешно оплачен");
                        }
                    }
                }
                case 2 -> {
                    orderChosen.setStatus(CANCELLED);
                    orderService.updateOrder(orderChosen);
                    System.out.println("Ваш заказ был успешно отменен");}
            }
        }
        catch (Exception e)
        {
            System.out.println("Пошло что-то не так " + e.getMessage());
        }
    }

    private void findOrder()
    {
        try {
            System.out.println("Найти заказы:");
            System.out.println("1. Вывести заказы по id человека");
            System.out.println("2. Вывести заказы по статусу заказа");
            System.out.println("3. Вывести заказы по диапозону сумм");
            System.out.println("4. Вывести все заказы");
            switch (makeChoice())
            {
                case 1 -> {
                    System.out.println("Доступные клиенты:");
                    clientService.getAllClients().forEach(System.out::println);
                    System.out.println("\nВыберите клиента по id");
                    Client client = clientService.getById(scanner.nextLong());
                    System.out.println("Все заказы " + client.getName() + " с id " + client.getId());
                    orderService.getOrdersByClientId(client.getId()).forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println("Выберете статус заказа:");
                    System.out.println("1. Processed");
                    System.out.println("2. Canceled");
                    System.out.println("3. Verified");
                    OderStatus status =
                            switch (makeChoice())
                    {
                        case 1 -> PROCESSED;
                        case 2 -> CANCELLED;
                        case 3 -> VERIFIED;
                        default -> {
                            System.out.println("Выбрано по умолчанию Processed");
                            yield PROCESSED;
                        }
                    };
                    System.out.println("Все заказы со статусом = " + status);
                    orderService.getOrdersByStatus(status).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("Выберете диапозон сумм:");
                    System.out.println("Минимальное значение");
                    int orderMin = scanner.nextInt();
                    System.out.println("Максимальное значение");
                    int orderMax = scanner.nextInt();
                    System.out.printf("Заказы в диапозоне от %d до %d\n ", orderMin, orderMax);
                    orderService.getOrdersByAmountRange(orderMin, orderMax).forEach(System.out::println);

                }
                case 4 -> {
                    System.out.println("Все заказы");
                    orderService.getAllOrders().forEach(System.out::println);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Что-то пошло не так " + e.getMessage());
        }
    }

    private void chooseCoupon()
    {
        try {
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name: %s, id: %d \t", client.getName(),client.getId());
                System.out.println("\nВыберите клиента по id");
                Client clientChosen = clientService.getById(scanner.nextLong());
                couponService.getAllCoupons().forEach(coupon -> {
                    System.out.printf("number: %d discount: %d %%", coupon.getCode(),coupon.getDiscount());
                });
                System.out.println("\nВыберите купон по id");
                Coupon coupon = couponService.getById(scanner.nextLong());
                couponService.enrollClientToCoupon(clientChosen.getId(), coupon.getId());
                System.out.println("Успешно выбран купон!");
            });
        }
        catch (Exception e)
        {
            System.out.println("Пошло что-то не так " + e.getMessage());
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
            while (true) {
                System.out.println("Что хотите поменять?");
                System.out.println("1. Имя");
                System.out.println("2. Емейл");
                System.out.println("3. Телефон");
                System.out.println("4. Адрес");
                System.out.println("5. Выход");
                switch (makeChoice())
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
                            clientService.updateClient(client);
                            profileService.saveProfile(profile);
                            transaction.commit();
                            System.out.println("Успешно сохранено!");
                            return;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("\nТакого не найдено " + e.getMessage());
        }
        }


    private void deleteClientWithProfile()
    {
        try{
            clientService.getAllClients().forEach(client -> {
                System.out.printf("name: %s id : %d \n", client.getName(), client.getId());
            });
            System.out.println("\nВыберите клиента по id");
            Client deleteClient = clientService.getByIdGraph(scanner.nextLong());
            clientService.deleteClient(deleteClient.getId());
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

            Client savedClient = clientService.saveClient(client);
            profile.setClient(savedClient);

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
            System.out.println("Пожалуйста, напишите число");
        }
        return 1;
    }
}
