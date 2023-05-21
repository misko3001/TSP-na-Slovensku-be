package mmojzis.zp.slovak_tsp.controller.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WaypointDto {

    @NotBlank(message = "Waypoint name cannot be blank")
    private String name;

    @NotNull(message = "Waypoint latitude cannot be null")
    @Min(value = -90, message = "Waypoint latitude cannot be less than -90")
    @Max(value = 90, message = "Waypoint latitude cannot be greater than 90")
    private Double latitude;

    @NotNull(message = "Waypoint longitude cannot be null")
    @Min(value = -180, message = "Waypoint longitude cannot be less than -180")
    @Max(value = 180, message = "Waypoint longitude cannot be greater than 180")
    private Double longitude;
}
