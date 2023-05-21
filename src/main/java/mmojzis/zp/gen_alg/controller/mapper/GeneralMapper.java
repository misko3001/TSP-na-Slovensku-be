package mmojzis.zp.gen_alg.controller.mapper;

import mmojzis.zp.gen_alg.controller.dto.TerminationDto;
import mmojzis.zp.gen_alg.domain.ExecutionTime;
import mmojzis.zp.gen_alg.domain.TerminationConditions;
import mmojzis.zp.gen_alg.utils.exception.MappingRuntimeException;
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
