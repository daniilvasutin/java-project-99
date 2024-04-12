package hexlet.code.controller;

import hexlet.code.dto.labelDTO.LabelCreateDTO;
import hexlet.code.dto.labelDTO.LabelDTO;
import hexlet.code.dto.labelDTO.LabelUpdateDTO;
import hexlet.code.service.LabelService;
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

@RestController
@RequestMapping("/api/labels")
public class LabelController {

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
