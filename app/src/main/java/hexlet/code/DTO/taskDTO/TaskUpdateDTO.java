package hexlet.code.DTO.taskDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {

    private JsonNullable<Integer> index;

//    @JsonProperty("assignee_id")
//    был assigneeId
    private JsonNullable<Long> assignee_id;

    @NotBlank
    private JsonNullable<String> title;

    private JsonNullable<String> content;

    @NotNull
    private JsonNullable<String> status;
}
