package abbas.project.hotel.model;

import javax.persistence.*;

@Entity
@Table(name = "loyalty_accounts")
public class LoyaltyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Guest guest;

    @Column(nullable = false)
    private int points;

    protected LoyaltyAccount() {}

    public LoyaltyAccount(Guest guest) {
        this.guest = guest;
        this.points = 0;
    }

    public Long getId() { return id; }
    public Guest getGuest() { return guest; }
    public int getPoints() { return points; }

    public void addPoints(int amount) { this.points += amount; }
    public void subtractPoints(int amount) { this.points -= amount; }
}
