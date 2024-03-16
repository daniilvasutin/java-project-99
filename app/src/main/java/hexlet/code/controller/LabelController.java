package hexlet.code.controller;

import hexlet.code.DTO.labelDTO.LabelCreateDTO;
import hexlet.code.DTO.labelDTO.LabelDTO;
import hexlet.code.DTO.labelDTO.LabelUpdateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> showAll() {
        var labels = labelRepository.findAll();
        var labelsDTO = labels.stream().map(label -> labelMapper.map(label)).toList();

        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(labels.size())).body(labelsDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO showById(@PathVariable Long id) {
        var label = labelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Label with id: " + id + " not found"));

        return labelMapper.map(label);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO createTaskStatus(@RequestBody LabelCreateDTO labelCreateDTO) {
        var label = labelMapper.map(labelCreateDTO);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO updateTaskStatus(@RequestBody LabelUpdateDTO labelUpdateDTO, @PathVariable Long id) {
        var label = labelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Label with id: " + id + " not found"));

        labelMapper.update(labelUpdateDTO, label);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskStatus(@PathVariable Long id) {
        labelRepository.deleteById(id);
    }




}
