package hexlet.code.controller;

import hexlet.code.DTO.userDTO.UserCreateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> show() {
        var users = userRepository.findAll();
        List<UserDTO> usersDTO = users.stream().map(user -> userMapper.map(user)).toList();
        System.out.println(usersDTO.get(0).getCreatedAt());

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(usersDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO showById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));

        UserDTO userDTO = userMapper.map(user);

        return userDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserCreateDTO createDTO) {
        User user = userMapper.map(createDTO);
        userRepository.save(user);
        return userMapper.map(user);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateDTO(@PathVariable long id, @RequestBody UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));

        userMapper.update(updateDTO, user);

        userRepository.save(user);

        return userMapper.map(user);

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userRepository.deleteById(id);
    }

}
