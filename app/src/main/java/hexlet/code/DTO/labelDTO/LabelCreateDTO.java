package hexlet.code.DTO.labelDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreateDTO {
    @NotBlank
    private String name;
}
