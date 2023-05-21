package mmojzis.zp.slovak_tsp.controller.dto;

import lombok.Data;
import mmojzis.zp.slovak_tsp.domain.ExecutionTime;
import mmojzis.zp.slovak_tsp.domain.ValidTermination;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ValidTermination
public class TerminationDto {

    @NotNull(message = "maxGenerations cannot be null")
    @Min(value = 1, message = "Minimum number of generations must be greater than 0")
    private Long maxGenerations;

    @Min(value = 1, message = "Minimum number of steady fitness generations must be greater than 0")
    private Integer maxSteadyFitnessGenerations;

    private ExecutionTime chronoUnit;

    @Min(value = 1, message = "ChronoValue must be greater than 0")
    private Long chronoValue;

    @Min(value = 1, message = "Minimum value of short filter must be greater than 0")
    private Integer shortFilter;

    @Min(value = 2, message = "Minimum value of long filter must be greater than 1")
    private Integer longFilter;

    @Min(value = 0, message = "Epsilon cannot be lower than 0")
    @Max(value = 1, message = "Epsilon cannot be higher than 1")
    private Double epsilon;
}
