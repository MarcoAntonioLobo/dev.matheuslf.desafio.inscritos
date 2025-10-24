package dev.matheuslf.desafio.inscritos.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreateDTO {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
