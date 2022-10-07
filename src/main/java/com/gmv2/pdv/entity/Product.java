package com.gmv2.pdv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo descrição e obrigatorio")
    private String description;

    @Column(length = 20, precision = 20, scale = 2, nullable = false)
    @NotNull(message = "O campo preço e obrigatorio")
    private BigDecimal price;

    @NotNull(message = "O campo quantidade e obrigatorio")
    @Column(nullable = true)
    private int quantity;
}
