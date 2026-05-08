package com.agustina.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private LocalDate expectedReturnDate;

    private LocalDate actualReturnDate;

    @ManyToOne
    @JoinColumn(name = "book_id") // La FK será book_id
    private Book book;
}
