package com.smartit.truckprojobs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "password")
    private String password;

    @Column(name = "registration_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;

    @ManyToOne
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id")
    private UsersType userTypeId;

    // constructors
    public Users() {
    }

    public Users(Long userId, String email, Boolean active, String password,
                 Date registrationDate, UsersType userTypeId) {
        this.userId = userId;
        this.email = email;
        this.active = active;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userTypeId = userTypeId;
    }
    // Getters and setters (optional)
}