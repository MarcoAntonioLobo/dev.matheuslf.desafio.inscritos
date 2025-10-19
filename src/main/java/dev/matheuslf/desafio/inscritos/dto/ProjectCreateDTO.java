package dev.matheuslf.desafio.inscritos.dto;

import java.time.LocalDate;

public record ProjectCreateDTO(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {}