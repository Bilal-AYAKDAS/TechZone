package com.developerteam.techzone.entities.concreates;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Table(name="user_adresses")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class UserAdress {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @Column(name="country")
    private String country;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="post_code")
    private String postCode;

    @Column(name="adress")
    private String adress;

}
