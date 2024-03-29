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
import hexlet.code.service.LabelService;
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

    @Autowired
    private LabelService labelService;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> showAll() {

        var labelsDTO = labelService.getAll();

        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(labelsDTO.size())).body(labelsDTO);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO showById(@PathVariable Long id) {

        return labelService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO createLabel(@RequestBody LabelCreateDTO labelCreateDTO) {

        return labelService.create(labelCreateDTO);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO updateTaskStatus(@RequestBody LabelUpdateDTO labelUpdateDTO, @PathVariable Long id) {

        return labelService.update(labelUpdateDTO, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {

        labelService.destroy(id);
    }
}
