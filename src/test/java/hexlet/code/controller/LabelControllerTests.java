package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.labelDTO.LabelDTO;
import hexlet.code.dto.labelDTO.LabelUpdateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LabelControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

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

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;

    @BeforeEach
    public void setUp() {

        token = jwt().jwt(builder -> builder.subject(UserUtils.ADMIN_EMAIL));
        testUtils.cleanAllRepository();

    }

    @Test
    public void testIndex() throws Exception {

        var firstLabel = testUtils.generateLabel();
        labelRepository.save(firstLabel);

        var secondLabel = testUtils.generateLabel();
        labelRepository.save(secondLabel);

        var result = mockMvc.perform(get("/api/labels").with(token))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();

    }

    @Test
    public void testShow() throws Exception {

        var testLabel = testUtils.generateLabel();
        labelRepository.save(testLabel);

        var request = get("/api/labels/{id}", testLabel.getId()).with(token);
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        var labelFromRepo = labelRepository.findById(testLabel.getId()).get();

        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(labelFromRepo.getName()));

    }

    @Test
    public void testCreate() throws Exception {

        var testLabel = testUtils.generateLabel();

        LabelDTO dto = labelMapper.map(testLabel);

        var request = post("/api/labels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto))
                .with(token);

        var result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();

        var body = result.getResponse().getContentAsString();
        var id = om.readTree(body).get("id").asLong();
        assertThat(labelRepository.findById(id)).isPresent();

        var labelFromRepo = labelRepository.findById(id).get();

        assertThat(labelFromRepo).isNotNull();
        assertThat(labelFromRepo.getName()).isEqualTo(testLabel.getName());

    }

    @Test
    public void testUpdate() throws Exception {

        var testLabel = testUtils.generateLabel();
        labelRepository.save(testLabel);

        var updatedDTO = new LabelUpdateDTO();
        updatedDTO.setName(JsonNullable.of("updated label name"));

        var request = put("/api/labels/{id}", testLabel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updatedDTO))
                .with(token);

        mockMvc.perform(request).andExpect(status().isOk());


        var updateLabel = labelRepository.findById(testLabel.getId()).get();

        assertThat(updateLabel).isNotNull();
        assertThat(updateLabel.getName()).isEqualTo(updatedDTO.getName().get());

    }

    @Test
    public void testDestroy() throws Exception {

        var testLabel = testUtils.generateLabel();
        labelRepository.save(testLabel);

        var request = delete("/api/labels/{id}", testLabel.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        Assertions.assertThat(labelRepository.existsById(testLabel.getId())).isFalse();

    }
}
