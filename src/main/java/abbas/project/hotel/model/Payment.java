package abbas.project.hotel.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime time;

    protected Payment() {}

    public Payment(Reservation reservation, PaymentMethod method,
                   double amount, LocalDateTime time) {
        this.reservation = reservation;
        this.method = method;
        this.amount = amount;
        this.time = time;
    }

    public Long getId() { return id; }
    public Reservation getReservation() { return reservation; }
    public PaymentMethod getMethod() { return method; }
    public double getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }
}
