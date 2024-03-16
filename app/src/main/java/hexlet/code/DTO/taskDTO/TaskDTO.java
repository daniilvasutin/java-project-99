package hexlet.code.DTO.taskDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import hexlet.code.model.Label;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskDTO {

    private Long id;
    private Integer index;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long assignee_id;
    private String title;
    private String content;
    private String status;

    private List<Long> taskLabelIds;
    //private List<Label> taskLabelIds; было зацикливание но работало
}
