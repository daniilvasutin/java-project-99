package hexlet.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "label")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Label implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=3)
    @Column(unique = true)
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

//    @JsonIgnore//разобраться !!!
    @ManyToMany(mappedBy = "labels", cascade = CascadeType.MERGE)
    private List<Task> tasks = new ArrayList<>();
}
