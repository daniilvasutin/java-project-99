package hexlet.code.DTO.taskStatusDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdateDTO {
    @NotBlank
    private JsonNullable<String> name;
    @NotBlank
    private JsonNullable<String> slug;
}
