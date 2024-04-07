package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.mapper.TaskStatusMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskStatusMapper tsMapper;

    @Autowired
    private TaskStatusUtil taskStatusUtil;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject(UserUtils.ADMIN_EMAIL));
        testUtils.cleanAllRepository();
    }

    @Test
    public void testIndex() throws Exception {

        var firstTaskStatus = testUtils.generateTaskStatus();
        taskStatusRepository.save(firstTaskStatus);

        var secondTaskStatus = testUtils.generateTaskStatus();
        taskStatusRepository.save(secondTaskStatus);

        var result = mockMvc.perform(get("/api/task_statuses").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        var testTaskStatus = testUtils.generateTaskStatus();
        taskStatusRepository.save(testTaskStatus);

        var request = get("/api/task_statuses/{id}", testTaskStatus.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        var taskStatus = taskStatusRepository.findById(testTaskStatus.getId()).get();

        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(taskStatus.getName()),
                v -> v.node("slug").isEqualTo(taskStatus.getSlug()));
    }

    @Test
    public void testCreate() throws Exception {

        var testTaskStatus = testUtils.generateTaskStatus();

        TaskStatusDTO dto = tsMapper.map(testTaskStatus);

        var request = post("/api/task_statuses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto))
                .with(token);

        mockMvc.perform(request).andExpect(status().isCreated());

        var taskStatusFromRepo = taskStatusRepository.findBySlug(testTaskStatus.getSlug()).get();

        assertThat(taskStatusFromRepo).isNotNull();
        assertThat(taskStatusFromRepo.getName()).isEqualTo(testTaskStatus.getName());
        assertThat(taskStatusFromRepo.getSlug()).isEqualTo(testTaskStatus.getSlug());

    }

    @Test
    public void testUpdate() throws Exception {

        var testTaskStatus = testUtils.generateTaskStatus();
        taskStatusRepository.save(testTaskStatus);

        var updatedDTO = new TaskStatusUpdateDTO();
        updatedDTO.setName(JsonNullable.of("update status name"));

        var request = put("/api/task_statuses/{id}", testTaskStatus.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedDTO))
                .with(token);

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTaskStatus = taskStatusRepository.findBySlug(testTaskStatus.getSlug()).get();

        assertThat(updatedTaskStatus).isNotNull();
        assertThat(updatedTaskStatus.getName()).isEqualTo(updatedDTO.getName().get());
        assertThat(updatedTaskStatus.getSlug()).isEqualTo(testTaskStatus.getSlug());
    }

    @Test
    public void testDestroy() throws Exception {

        var testTaskStatus = testUtils.generateTaskStatus();
        taskStatusRepository.save(testTaskStatus);

        var request = delete("/api/task_statuses/{id}", testTaskStatus.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Assertions.assertThat(taskStatusRepository.existsById(testTaskStatus.getId())).isFalse();
    }
// можно раскоментить когда поключу авторизацию !!!дописать тест когда статус используется в таске
//    @Test
//    public void testIndexWithoutAuth() throws Exception {
//
//        var result = mockMvc.perform(get("/api/task_statuses"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testShowWithoutAuth() throws Exception {
//        var testTaskStatus = testUtils.generateTaskStatus();
//        taskStatusRepository.save(testTaskStatus);
//
//        var request = get("/api/task_statuses/{id}", testTaskStatus.getId());
//        var result = mockMvc.perform(request)
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testCreateWithoutAuth() throws Exception {
//        var testTaskStatus = testUtils.generateTaskStatus();
//
//        var dto = tsMapper.map(testTaskStatus);
//
//        var request = post("/api/task_statuses")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto));
//
//        mockMvc.perform(request)
//                .andExpect(status().isUnauthorized());
//    }
}
