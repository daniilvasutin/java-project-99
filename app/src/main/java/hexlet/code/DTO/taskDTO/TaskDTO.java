package hexlet.code.DTO.taskDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDTO {

//        "id": 1,
//        "index": 3140,
//        "createdAt": "2023-07-30",
//        "assignee_id": 1,
//        "title": "Task 1",
//        "content": "Description of task 1",
//        "status": "to_be_fixed"

    private Long id;
    private Integer index;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long assignee_id;
    private String title;
    private String content;
    private String status;
}
