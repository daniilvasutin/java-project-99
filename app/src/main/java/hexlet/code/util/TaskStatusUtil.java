package hexlet.code.util;

import hexlet.code.model.TaskStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskStatusUtil {
    @Bean
    public List<TaskStatus> getDefaultTaskStatus() {
        return List.of(createTaskStatus("Draft", "draft"),
                createTaskStatus("To review", "to_review"),
                createTaskStatus("To be fixed", "to_be_fixed"),
                createTaskStatus("To publish", "to_publish"),
                createTaskStatus("Published", "published"));
    }

    private TaskStatus createTaskStatus(String draft, String slug) {
        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setName(draft);
        taskStatus.setSlug(slug);

        return taskStatus;
    }
}
