package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.DTO.userDTO.UserCreateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.mapper.UserMapper;
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
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserMapper userMapper;

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

        var firstUser = testUtils.generateUser();
        userRepository.save(firstUser);

        var secondUser = testUtils.generateUser();
        userRepository.save(secondUser);

        var result = mockMvc.perform(get("/api/users").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        var user = testUtils.generateUser();
        userRepository.save(user);

        var request = get("/api/users/{id}", user.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        var userFromRepo = userRepository.findById(user.getId()).get();

        assertThatJson(body).and(
                v -> v.node("firstName").isEqualTo(userFromRepo.getFirstName()),
                v -> v.node("lastName").isEqualTo(userFromRepo.getLastName()),
                v -> v.node("email").isEqualTo(userFromRepo.getEmail()));
    }

    @Test
    public void testCreate() throws Exception {

        var user = testUtils.generateUser();
        var userPasswordNoEncode = user.getPassword();

        UserDTO dto = userMapper.map(user);

        var request = post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user))
                .with(token);

        mockMvc.perform(request).andExpect(status().isCreated());

        var userFromRepo = userRepository.findByEmail(user.getEmail()).get();

        assertThat(userFromRepo).isNotNull();
        assertThat(userFromRepo.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userFromRepo.getLastName()).isEqualTo(user.getLastName());
        assertThat(userFromRepo.getEmail()).isEqualTo(user.getEmail());
        assertThat(userFromRepo.getPassword()).doesNotMatch(userPasswordNoEncode);
    }

    @Test
    public void testUpdate() throws Exception{

        var user = testUtils.generateUser();
        var userPasswordNoEncode = user.getPassword();
        userRepository.save(user);

        var newFirstName = "updated firstname";
        var updatedDTO = new UserUpdateDTO();
        updatedDTO.setFirstName(JsonNullable.of(newFirstName));

        var request = put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedDTO))
                .with(token);

        mockMvc.perform(request).andExpect(status().isOk());

        var userFromRepo = userRepository.findByEmail(user.getEmail()).get();

        assertThat(userFromRepo).isNotNull();
        assertThat(userFromRepo.getFirstName()).isEqualTo(newFirstName);
        assertThat(userFromRepo.getLastName()).isEqualTo(user.getLastName());
        assertThat(userFromRepo.getEmail()).isEqualTo(user.getEmail());
        assertThat(userFromRepo.getPassword()).doesNotMatch(userPasswordNoEncode);
    }

    @Test
    public void testDestroy() throws Exception {

        var user = testUtils.generateUser();
        userRepository.save(user);

        var request = delete("/api/users/{id}", user.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Assertions.assertThat(userRepository.existsById(user.getId())).isFalse();
    }
}
