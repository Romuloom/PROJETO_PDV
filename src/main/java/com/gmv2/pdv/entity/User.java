package com.gmv2.pdv.entity;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @NotBlank(message = "Campo nome é obrigatorio")
    private String name;
    private boolean isEnabled;
    @OneToMany(mappedBy = "user")
    private List<Sale> sales;
}
