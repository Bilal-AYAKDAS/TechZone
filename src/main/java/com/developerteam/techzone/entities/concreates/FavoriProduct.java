package com.developerteam.techzone.entities.concreates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name="favori_products")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class FavoriProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private Product product;


    private Date addDate;
}
