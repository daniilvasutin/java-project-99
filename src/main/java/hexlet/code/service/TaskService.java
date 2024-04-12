package hexlet.code.service;

import hexlet.code.dto.taskDTO.TaskCreateDTO;
import hexlet.code.dto.taskDTO.TaskDTO;
import hexlet.code.dto.taskDTO.TaskParamsDTO;
import hexlet.code.dto.taskDTO.TaskUpdateDTO;
import hexlet.code.Specification.TaskSpecification;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskSpecification taskSpecification;

    public List<TaskDTO> getAll(TaskParamsDTO paramsDTO) {

        var spec = taskSpecification.build(paramsDTO);
        var tasks = taskRepository.findAll(spec);

        List<TaskDTO> tasksDTO = tasks.stream().map(task -> taskMapper.map(task)).toList();
        return tasksDTO;
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id: " + id + " not found"));

        return taskMapper.map(task);
    }

    public TaskDTO create(TaskCreateDTO taskCreateDTO) {

        Task task = taskMapper.map(taskCreateDTO);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskUpdateDTO, Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "task with " + id + " not found"));
        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public void destroy(Long id) {
        taskRepository.deleteById(id);
    }
}
