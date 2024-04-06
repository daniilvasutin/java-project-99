package hexlet.code.controller;

import hexlet.code.DTO.taskDTO.TaskCreateDTO;
import hexlet.code.DTO.taskDTO.TaskDTO;
import hexlet.code.DTO.taskDTO.TaskParamsDTO;
import hexlet.code.DTO.taskDTO.TaskUpdateDTO;
import hexlet.code.Specification.TaskSpecification;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.sentry.Sentry;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> show(TaskParamsDTO paramsDTO) {



        try {
            throw new Exception("This is a test sentry form task");
        } catch (Exception e) {
            Sentry.captureException(e);
        }


//        var spec = taskSpecification.build(paramsDTO);
//        List<Task> tasksWithParams = taskRepository.findAll(spec);
//        var tasksDTO = tasksWithParams.stream().map(task -> taskMapper.map(task)).toList();



//                List<Task> tasks = taskRepository.findAll();
//        List<TaskDTO> tasksDTO = tasks.stream().map(task -> taskMapper.map(task)).toList();

        List<TaskDTO> tasksDTO = taskService.getAll(paramsDTO);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasksDTO.size()))
                .body(tasksDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO showById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.create(taskCreateDTO);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@RequestBody TaskUpdateDTO taskUpdateDTO, @PathVariable Long id) {
        return taskService.update(taskUpdateDTO, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable Long id) {
        taskService.destroy(id);
    }
}