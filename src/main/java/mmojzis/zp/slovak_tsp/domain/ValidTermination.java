package mmojzis.zp.slovak_tsp.domain;

import mmojzis.zp.slovak_tsp.common.TerminationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TerminationValidator.class)
public @interface ValidTermination {

    String message() default "Termination conditions are not valid - requiered fields were null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
