package hexlet.code.DTO.taskDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {
//            "index": 12,
//            "assignee_id": 1,
//            "title": "Test title",
//            "content": "Aaaaaaaa",
//            "status": "draft"

    private Integer index;

    @NotNull
    private Long assignee_id;

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private String status;
}
