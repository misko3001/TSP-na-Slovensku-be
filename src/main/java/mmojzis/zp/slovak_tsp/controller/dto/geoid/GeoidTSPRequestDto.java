package mmojzis.zp.slovak_tsp.controller.dto.geoid;

import lombok.Data;
import mmojzis.zp.slovak_tsp.controller.dto.TerminationDto;
import mmojzis.zp.slovak_tsp.controller.dto.WaypointDto;
import mmojzis.zp.slovak_tsp.domain.TSPCrossover;
import mmojzis.zp.slovak_tsp.domain.TSPMutator;
import mmojzis.zp.slovak_tsp.domain.TSPSelector;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class GeoidTSPRequestDto {

    @NotEmpty(message = "List of waypoints cannot be empty")
    @Size(min = 2, message = "Request must contain at least two points")
    @Valid
    private List<WaypointDto> waypoints;

    @NotNull(message = "termination cannot be null")
    @Valid
    private TerminationDto termination;

    @NotNull(message = "populationSize cannot be null")
    @Min(value = 1, message = "Minimum population size must be greater than 0")
    private Integer populationSize;

    @NotNull(message = "offspringFraction cannot be null")
    @Min(value = 0, message = "Offspring fraction cannot be lower than 0")
    @Max(value = 1, message = "Offspring fraction cannot be higher than 1")
    private Double offspringFraction;

    @NotEmpty(message = "List of selectors cannot be empty")
    @Size(min = 2, max = 2, message = "Request must contain exactly two selectors")
    private List<TSPSelector> selectors;

    @NotEmpty(message = "List of selection modifiers cannot be empty")
    @Size(min = 2, max = 2, message = "Request must contain exactly two selection modifiers")
    private List<String> selectionModifiers;

    @NotEmpty(message = "List of number of elites cannot be empty")
    @Size(min = 2, max = 2, message = "Request must contain exactly two numbers of elites")
    private List<Integer> numberOfElites;

    @NotNull(message = "Mutator cannot be empty")
    private TSPMutator mutator;

    private String mutationModifier;

    @NotNull(message = "Crossover cannot be empty")
    private TSPCrossover crossover;

    private String crossoverModifier;
}
