package com.example.mindmingle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class BadWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBadWord;
    private String content;
}
