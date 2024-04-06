package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.util.TaskStatusUtil;
import hexlet.code.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusUtil taskStatusUtil;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var admin = userUtils.createAdmin();
        userService.createUser(admin);

        var taskStatuses = taskStatusUtil.getDefaultTaskStatus();
        taskStatuses.stream().forEach(taskStatus -> taskStatusRepository.save(taskStatus));

        List<Label> lables = new ArrayList<>();
        var label1 = new Label();
        label1.setName("feature");
        lables.add(label1);
        var label2 = new Label();
        label2.setName("bug");
        lables.add(label2);
        lables.stream().forEach(label -> labelRepository.save(label));


        var task = new Task();
        task.setName("Task 1");
        task.setDescription("Description of task 1");
        task.setIndex(3140);
        task.setAssignee(userRepository.findByEmail("hexlet@example.com").get());
        taskRepository.save(task);

        var task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Description of task 2");
        task2.setIndex(3161);
        task2.setAssignee(userRepository.findByEmail("hexlet@example.com").get());
        taskRepository.save(task2);
    }
}
