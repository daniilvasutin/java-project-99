package hexlet.code.dto.taskStatusDTO;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
//import java.time.LocalDateTime;

@Getter
@Setter
public class TaskStatusDTO {
    private long id;
    private String name;
    private String slug;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
