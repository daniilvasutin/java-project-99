package hexlet.code.dto.labelDTO;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
//import java.time.LocalDateTime;

@Getter
@Setter
public class LabelDTO {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate createdAt;
}
