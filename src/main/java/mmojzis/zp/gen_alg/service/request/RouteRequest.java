package mmojzis.zp.gen_alg.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {

    @NotBlank(message = "Name of the start point cannot be blank")
    private String startPoint;

    @NotBlank(message = "Name of the end point cannot be blank")
    private String endPoint;
}
