package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DTO.labelDTO.LabelDTO;
import hexlet.code.DTO.labelDTO.LabelUpdateDTO;
import hexlet.code.DTO.taskDTO.TaskDTO;
import hexlet.code.DTO.taskDTO.TaskUpdateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.testUtils.TestUtils;
import hexlet.code.util.TaskStatusUtil;
import hexlet.code.util.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskStatusMapper tsMapper;

    @Autowired
    private TaskStatusUtil taskStatusUtil;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private TaskMapper taskMapper;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject(UserUtils.ADMIN_EMAIL));
        testUtils.cleanAllRepository();
    }

    @Test
    public void testIndex() throws Exception {

        var firstTask = testUtils.generateTask();
        taskRepository.save(firstTask);

        var secondTask = testUtils.generateTask();
        taskRepository.save(secondTask);

        var result = mockMvc.perform(get("/api/tasks").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();

        var firstTaskFromRepo = taskRepository.findById(firstTask.getId()).get();
        System.out.print(firstTaskFromRepo.getAssignee().getId() + " == ");
        System.out.println(firstTask.getAssignee().getId());
        assertThat(firstTaskFromRepo.getAssignee().getId()).isEqualTo(firstTask.getAssignee().getId());
    }

    @Test
    public void testShow() throws Exception {

        var task = testUtils.generateTask();
        taskRepository.save(task);

        var request = get("/api/tasks/{id}", task.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        var taskFromRepo = taskRepository.findById(task.getId()).get();
        var kk = taskFromRepo.getName();
        var tt = taskFromRepo.getDescription();

        assertThatJson(body).and(
                v -> v.node("title").isEqualTo(taskFromRepo.getName()),
                v -> v.node("content").isEqualTo(taskFromRepo.getDescription()),
                v -> v.node("assignee_id").isEqualTo(taskFromRepo.getAssignee().getId()),
                v -> v.node("status").isEqualTo(taskFromRepo.getTaskStatus().getSlug()));
//                v -> v.node("taskLabelIds").isEqualTo(taskFromRepo.getLabels()));
    }

    @Test
    public void testCreate() throws Exception {

        var task = testUtils.generateTask();

        TaskDTO dto = taskMapper.map(task);

        var request = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto))
                .with(token);

        var result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();

        var body = result.getResponse().getContentAsString();
        var id = om.readTree(body).get("id").asLong();
        assertThat(taskRepository.findById(id)).isPresent();

        var taskFromRepo = taskRepository.findById(id).get();

        assertThat(taskFromRepo).isNotNull();
        assertThat(taskFromRepo.getName()).isEqualTo(task.getName());
    }

    @Test
    public void testUpdate() throws Exception{

        var task = testUtils.generateTask();
        taskRepository.save(task);

        var updatedDTO = new TaskUpdateDTO();
        updatedDTO.setTitle(JsonNullable.of("updated task name"));

        var request = put("/api/tasks/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedDTO))
                .with(token);

        mockMvc.perform(request).andExpect(status().isOk());


        var updatedTaskFromRepo = taskRepository.findById(task.getId()).get();

        assertThat(updatedTaskFromRepo).isNotNull();
        assertThat(updatedTaskFromRepo.getName()).isEqualTo(updatedDTO.getTitle().get());
        assertThat(updatedDTO.getContent()).isNull();
    }

    @Test
    public void testDestroy() throws Exception {

        var task = testUtils.generateTask();
        taskRepository.save(task);

        var request = delete("/api/tasks/{id}", task.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Assertions.assertThat(taskRepository.existsById(task.getId())).isFalse();
    }
}
