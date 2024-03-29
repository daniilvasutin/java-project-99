package hexlet.code.service;

import hexlet.code.DTO.userDTO.UserCreateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        List<UserDTO> usersDTO = users.stream().map(user -> userMapper.map(user)).toList();

        return usersDTO;
    }

    public UserDTO findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        return userMapper.map(user);
    }


    public UserDTO create(UserCreateDTO createDTO) {
        User user = userMapper.map(createDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        UserDTO userDTO = userMapper.map(user);
        
        return userDTO;
    }


    public UserDTO update(UserUpdateDTO updateDTO, long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userMapper.update(updateDTO, user);

        userRepository.save(user);

        UserDTO userDTO = userMapper.map(user);

        return userDTO;
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
