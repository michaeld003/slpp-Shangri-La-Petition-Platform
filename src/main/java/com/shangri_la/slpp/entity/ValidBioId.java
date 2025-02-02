package com.shangri_la.slpp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Data
@NoArgsConstructor
@Entity
@Table(name = "valid_bioids")
public class ValidBioId {
    @Id
    private String bioid;

    @Column(name = "is_used")
    private Boolean isUsed = false;
}