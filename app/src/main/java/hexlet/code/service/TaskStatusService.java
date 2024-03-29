package hexlet.code.service;

import hexlet.code.DTO.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    public List<TaskStatusDTO> getAll() {
        var status = taskStatusRepository.findAll();
        var statuses = status.stream().map(stat -> taskStatusMapper.map(stat)).toList();

        return statuses;
    }

    public TaskStatusDTO findById(Long id) {
        var status = taskStatusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Status with id: " + id + " not found"));

        return taskStatusMapper.map(status);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO taskStatusCreateDTO) {
        TaskStatus taskStatus = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus);

        return taskStatusMapper.map(taskStatus);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO taskStatusUpdateDTO, Long id) {
        var taskStatus = taskStatusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task status with id: " + id + " not found"));

        taskStatusMapper.update(taskStatusUpdateDTO, taskStatus);
        taskStatusRepository.save(taskStatus);

        return taskStatusMapper.map(taskStatus);
    }

    public void destroy(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
