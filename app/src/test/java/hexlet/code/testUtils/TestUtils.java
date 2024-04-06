package hexlet.code.testUtils;

import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestUtils {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private Faker faker;

    @Bean
    public void cleanAllRepository() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @Bean
    public TaskStatus generateTaskStatus() {
        var generatedNameAndSlug = faker.lorem().words(2);
        var name = String.join(" ", generatedNameAndSlug);
        var slug = String.join("_", generatedNameAndSlug);

        var taskStatus = new TaskStatus();

        taskStatus.setName(name);
        taskStatus.setSlug(slug);

        return taskStatus;
    }

    @Bean
    public Label generateLabel() {
        var title = faker.lorem().fixedString(10);
        var label = new Label();
        label.setName(title);

        return label;
    }


    public User generateUser() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        var email = faker.internet().emailAddress();
        var password = faker.internet().password();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public Task generateTask() {
        var name = faker.lorem().fixedString(10);
        var description = faker.lorem().paragraph();
        var assignee = generateUser();
        userRepository.save(assignee);

        var taskStatus = generateTaskStatus();
        taskStatusRepository.save(taskStatus);
        //второй лейб удалить
        var labels = List.of(generateLabel(), generateLabel());
        labels.stream().forEach(label -> labelRepository.save(label));

        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setTaskStatus(taskStatus);
        task.setAssignee(assignee);
        task.setLabels(labels);

        return task;
    }
}
