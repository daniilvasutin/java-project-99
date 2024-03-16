package hexlet.code.controller;

import hexlet.code.DTO.taskDTO.TaskCreateDTO;
import hexlet.code.DTO.taskDTO.TaskDTO;
import hexlet.code.DTO.taskDTO.TaskUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> show() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> tasksDTO = tasks.stream().map(task -> taskMapper.map(task)).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasks.size()))
                .body(tasksDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO showById(@PathVariable Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task with id: " + id + " not found"));
        TaskDTO taskDTO = taskMapper.map(task);

        return taskDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        Task task = taskMapper.map(taskCreateDTO);


//        task.setLabels(taskCreateDTO.getTaskLabelIds());
        taskRepository.save(task);
        var dto = taskMapper.map(task);

//        return taskMapper.map(task);
        return dto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("task with " + id + " not found"));
        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
