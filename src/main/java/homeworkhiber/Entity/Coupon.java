package homeworkhiber.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Table(name = "coupon")
public class Coupon
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private Long code;

    @Column(name = "discount")
    private int discount;

    @ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private List<Client> client;

    public Coupon(Long code, int discount, Client client) {
        this.code = code;
        this.discount = discount;
    }

    public Coupon() {
    }

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}