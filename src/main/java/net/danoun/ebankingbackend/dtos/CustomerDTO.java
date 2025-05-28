package net.danoun.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CustomerDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
