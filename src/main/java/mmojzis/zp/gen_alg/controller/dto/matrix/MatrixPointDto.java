package mmojzis.zp.gen_alg.controller.dto.matrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrixPointDto {

    @NotBlank(message = "Name of the point cannot be blank")
    private String name;
}
