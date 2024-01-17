package ma.work.springsecurity6.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ourUsers")
public class OurUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;
}
