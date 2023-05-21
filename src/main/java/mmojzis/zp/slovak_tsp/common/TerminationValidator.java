package mmojzis.zp.slovak_tsp.common;

import lombok.extern.slf4j.Slf4j;
import mmojzis.zp.slovak_tsp.controller.dto.TerminationDto;
import mmojzis.zp.slovak_tsp.domain.ExecutionTime;
import mmojzis.zp.slovak_tsp.domain.ValidTermination;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class TerminationValidator implements ConstraintValidator<ValidTermination, TerminationDto> {

    @Override
    public boolean isValid(TerminationDto dto, ConstraintValidatorContext context) {
        boolean fitnessValid = isFitnessConvergenceValid(dto.getEpsilon(), dto.getShortFilter(), dto.getLongFilter());
        boolean timeValid = isExecutionTimeValid(dto.getChronoValue(), dto.getChronoUnit());

        if (!fitnessValid) {
            log.error("Fitness convergence termination condition not valid - missing fields");
        }
        if (!timeValid) {
            log.error("Execution time termination condition not valid - missing fields");
        }

        return fitnessValid && timeValid;
    }

    private boolean isExecutionTimeValid(Long val, ExecutionTime chronoUnit) {
        if (val != null || chronoUnit != null) {
            return val != null && chronoUnit != null;
        }
        return true;
    }

    private boolean isFitnessConvergenceValid(Double epsilon, Integer shortFilter, Integer longFilter) {
        boolean valid = true;
        if (epsilon != null || shortFilter != null || longFilter != null) {
            valid = epsilon != null && shortFilter != null && longFilter != null;
            if (valid && shortFilter >= longFilter) {
                log.error("Short filter is greater or equal to long filter ({}>={})", shortFilter, longFilter);
                valid = false;
            }
        }
        return valid;
    }
}