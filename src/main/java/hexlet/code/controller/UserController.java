package hexlet.code.controller;

import hexlet.code.dto.userDTO.UserCreateDTO;
import hexlet.code.dto.userDTO.UserDTO;
import hexlet.code.dto.userDTO.UserUpdateDTO;
import hexlet.code.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//import io.sentry.Sentry;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> show() {

//        try {
//            throw new Exception("This is a test sentry form users");
//        } catch (Exception e) {
//            Sentry.captureException(e);
//        }

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
    public UserDTO createUser(@RequestBody @Valid UserCreateDTO createDTO) {

        UserDTO userDTO = userService.create(createDTO);
        return userDTO;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateDTO(@PathVariable long id, @RequestBody @Valid UserUpdateDTO updateDTO) {

        UserDTO userDTO = userService.update(updateDTO, id);
        return userDTO;

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userService.deleteById(id);
    }

}
