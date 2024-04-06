package hexlet.code.controller;

import hexlet.code.DTO.userDTO.UserCreateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> show() {

        try {
            throw new Exception("This is a test sentry form users");
        } catch (Exception e) {
            Sentry.captureException(e);
        }

        var usersDTO = userService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(usersDTO.size()))
                .body(usersDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO showById(@PathVariable Long id) {

        var userDTO = userService.findById(id);
        return userDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserCreateDTO createDTO) {

        UserDTO userDTO = userService.create(createDTO);

        return userDTO;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateDTO(@PathVariable long id, @RequestBody UserUpdateDTO updateDTO) {


//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        userMapper.update(updateDTO, user);
//
//        userRepository.save(user);

        UserDTO userDTO = userService.update(updateDTO, id);

        return userDTO;

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userService.deleteById(id);
    }

}
