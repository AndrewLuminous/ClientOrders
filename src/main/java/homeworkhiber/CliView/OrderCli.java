package homeworkhiber.CliView;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Order;
import homeworkhiber.Entity.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class OrderCli
{
    private final Scanner scanner;


    public OrderCli(Scanner scanner) {
        this.scanner = scanner;
    }
    public Order createOrder(Client client)
    {
        System.out.println("Какова ваша сумма заказа?");
        int totalAmount = scanner.nextInt();
        return new Order(totalAmount, client, LocalDateTime.now());

    }
}
