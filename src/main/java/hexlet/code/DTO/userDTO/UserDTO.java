package hexlet.code.DTO.userDTO;

//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

//import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.LocalDate;
//import java.util.Date;
//import java.util.Date;
//import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private long id;
    private String email;
    private String firstName;
    private String lastName;

//    @JsonSerialize()
//    @JsonDeserialize()
//    @JsonFormat(pattern = "dd-M-yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en_GB")
    private LocalDate createdAt;
}
