package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class Task implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    private int index;

    private String description;

    @ManyToOne
    @NotNull
    private TaskStatus taskStatus;

    @ManyToOne
    private User assignee;

    @CreatedDate
    private LocalDateTime createdAt;
}
