package homeworkhiber.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_profile")
    private String phoneNumber;

    @Column(name = "address_profile")
    private String address;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Profile(String phoneNumber, String address, Client client)
    {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.client = client;
    }

    public Profile() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}