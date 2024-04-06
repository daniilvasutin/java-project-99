package hexlet.code.Specification;

import hexlet.code.DTO.taskDTO.TaskParamsDTO;
import hexlet.code.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {

    public Specification<Task> build(TaskParamsDTO params) {
        return withAssigneeId(params.getAssigneeId())
                .and(titleContaining(params.getTitleCont())
                .and(withStatus(params.getStatus()))
                .and(withLabelId(params.getLabelId())));
    }

    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, criteriaBuilder) -> assigneeId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
    }

    private Specification<Task> titleContaining(String subString) {
        return ((root, query, criteriaBuilder) -> (subString == null || subString.equals(""))
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("name").as(String.class), "%" + subString + "%"));
    }

    private Specification<Task> withStatus(String statusSlag) {
        return ((root, query, criteriaBuilder) -> (statusSlag == null || statusSlag.equals(""))
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("taskStatus").get("slug").as(String.class), statusSlag));
    }

    private Specification<Task> withLabelId(Long labelId) {
        return ((root, query, criteriaBuilder) -> labelId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("labels").get("id"), labelId));
    }
}
