package homeworkhiber.CliView;

import homeworkhiber.Entity.Client;
import homeworkhiber.Entity.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class ProfileCli
{
    private final Scanner scanner;

    public ProfileCli(Scanner scanner) {
        this.scanner = scanner;
    }

    public Profile createProfile(Client client)
    {
        System.out.println("Какой ваш номер телефона?");
        String phoneNumber = scanner.nextLine();
        System.out.println("Введите ваш адресс");
        String adress = scanner.nextLine();
        return new Profile(phoneNumber,adress, client);

    }
}
