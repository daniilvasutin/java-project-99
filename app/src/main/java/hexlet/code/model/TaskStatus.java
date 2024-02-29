package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TaskStatus")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min=1)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Size(min=1)
    @Column(unique = true)
    private String slug;

    @CreatedDate
    private LocalDateTime createdAt;
}
