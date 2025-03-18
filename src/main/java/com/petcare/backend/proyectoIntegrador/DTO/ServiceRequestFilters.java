package com.petcare.backend.proyectoIntegrador.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestFilters {
    LocalDate singleDate;
    LocalDate startDate;
    LocalDate endDate;
    String serviceName;
    Integer petsQty;
    String categoryName;
}
