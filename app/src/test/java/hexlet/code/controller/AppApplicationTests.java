package hexlet.code.controller;

import hexlet.code.DTO.UserCreateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
//import hexlet.code.util.Encode;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Faker faker;

	@Autowired
	private ObjectMapper om;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper mapper;

	private User testUser;

	@BeforeEach
	public void setUp() {
		testUser = Instancio.of(User.class)
				.ignore(Select.field(User::getId))
				.supply(Select.field(User::getFirstName), () -> faker.name().firstName())
				.supply(Select.field(User::getLastName), () -> faker.name().lastName())
				.supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
				.supply(Select.field(User::getPassword), () -> faker.internet().password())
				.create();
	}

	@Test
	public void testWelcomePage() throws Exception {
		var result = mockMvc.perform(get("/welcome"))
				.andExpect(status().isOk())
				.andReturn();

		var body = result.getResponse().getContentAsString();
		assertThat(body).contains("Welcome to Spring!");
	}

	@Test
	public void testIndex() throws Exception {
		userRepository.save(testUser);
		var result = mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andReturn();

		var body = result.getResponse().getContentAsString();
		assertThatJson(body).isArray();
	}

	@Test
	public void testShow() throws Exception {

		userRepository.save(testUser);

		var request = get("/api/users/{id}", testUser.getId());
		var result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		var body = result.getResponse().getContentAsString();

		assertThatJson(body).and(
				v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
				v -> v.node("email").isEqualTo(testUser.getEmail())
		);
	}

	@Test
	public void testCreate() throws Exception {
//		var dto = mapper.map(testUser);
		var dto = testUser;

		var request = post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(dto));

		mockMvc.perform(request)
				.andExpect(status().isCreated());

		var user = userRepository.findByEmail(testUser.getEmail()).get();

		assertThat(user).isNotNull();
		assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
		assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
	}
//
	@Test
	public void testCreateWithNotValidEmail() throws Exception {
		testUser.setEmail("qwert");

		var request = post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(testUser));

		mockMvc.perform(request)
				.andExpect(status().isBadRequest());
	}
//
//	@Test
//	public void testCreateWithNotValidPhone() throws Exception {
//		var dto = mapper.map(testGuest);
//		dto.setPhoneNumber("+12345678912w");
//
//		var request = post("/guests")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(om.writeValueAsString(dto));
//
//		mockMvc.perform(request)
//				.andExpect(status().isBadRequest());
//	}
//
//	@Test
//	public void testCreateWithNotValidCard() throws Exception {
//		var dto = mapper.map(testGuest);
//		dto.setClubCard("12");
//
//		var request = post("/guests")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(om.writeValueAsString(dto));
//
//		mockMvc.perform(request)
//				.andExpect(status().isBadRequest());
//	}
//
//	@Test
//	public void testCreateWithExpiredCard() throws Exception {
//		var dto = mapper.map(testGuest);
//		dto.setCardValidUntil(faker.date().past(10, TimeUnit.DAYS).toLocalDateTime().toLocalDate());
//
//		var request = post("/guests")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(om.writeValueAsString(dto));
//
//		mockMvc.perform(request)
//				.andExpect(status().isBadRequest());
//	}

	@Test
	public void testUpdate() throws Exception {
		userRepository.save(testUser);

		var dto = mapper.map(testUser);

		dto.setFirstName("new name");
		dto.setLastName("new last name");
		dto.setEmail("newEmail@gmail.com");

		var request = put("/api/users/{id}", testUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(dto));

		mockMvc.perform(request)
				.andExpect(status().isOk());

		var user = userRepository.findById(testUser.getId()).get();

		assertThat(user.getFirstName()).isEqualTo(dto.getFirstName());
		assertThat(user.getLastName()).isEqualTo(dto.getLastName());
		assertThat(user.getEmail()).isEqualTo(dto.getEmail());
		assertThat(user.getPassword()).isEqualTo(testUser.getPassword());//удалить позже!!!
	}

	@Test
	public void testPartialUpdate() throws Exception {
		userRepository.save(testUser);

		var dto = new HashMap<String, String>();
		dto.put("firstName", "another first name");

		var request = put("/api/users/{id}", testUser.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(dto));

		mockMvc.perform(request)
				.andExpect(status().isOk());

		var author = userRepository.findById(testUser.getId()).get();

		assertThat(author.getLastName()).isEqualTo(testUser.getLastName());
		assertThat(author.getFirstName()).isEqualTo(dto.get("firstName"));
	}

	@Test
	public void testDestroy() throws Exception {
		userRepository.save(testUser);
		var request = delete("/api/users/{id}", testUser.getId());
		mockMvc.perform(request)
				.andExpect(status().isNoContent());

		assertThat(userRepository.existsById(testUser.getId())).isEqualTo(false);
	}

}
