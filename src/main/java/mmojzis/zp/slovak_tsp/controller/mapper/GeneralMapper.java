package mmojzis.zp.slovak_tsp.controller.mapper;

import mmojzis.zp.slovak_tsp.controller.dto.TerminationDto;
import mmojzis.zp.slovak_tsp.domain.ExecutionTime;
import mmojzis.zp.slovak_tsp.domain.TerminationConditions;
import mmojzis.zp.slovak_tsp.utils.exception.MappingRuntimeException;
import org.mapstruct.Mapper;

import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    TerminationConditions toConditions(TerminationDto dto);

    default ChronoUnit toChronoUnit(ExecutionTime time) {
        if (time != null) {
            switch (time) {
                case MILLIS -> {
                    return ChronoUnit.MILLIS;
                }
                case SECONDS -> {
                    return ChronoUnit.SECONDS;
                }
                case MINUTES -> {
                    return ChronoUnit.MINUTES;
                }
                case HOURS -> {
                    return ChronoUnit.HOURS;
                }
                default -> throw new MappingRuntimeException("Invalid ExecutionTime type: " + time);
            }
        }
        return null;
    }
}
