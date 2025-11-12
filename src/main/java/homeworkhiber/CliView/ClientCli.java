package homeworkhiber.CliView;

import homeworkhiber.Entity.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class ClientCli
{
    private final Scanner scanner;

    public ClientCli(Scanner scanner) {
        this.scanner = scanner;
    }

    public Client createCLient()
    {
        System.out.println("Какое ваше имя?");
        String name = scanner.nextLine();
        System.out.println("Введите ваш email");
        String email = scanner.nextLine();
        scanner.close();
        return new Client(name,email, LocalDateTime.now());

    }
}
