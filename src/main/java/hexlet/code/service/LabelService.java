package hexlet.code.service;

import hexlet.code.dto.labelDTO.LabelCreateDTO;
import hexlet.code.dto.labelDTO.LabelDTO;
import hexlet.code.dto.labelDTO.LabelUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelRepository labelRepository;

    public List<LabelDTO> getAll() {
        var labels = labelRepository.findAll();
        var labelsDTO = labels.stream().map(label -> labelMapper.map(label)).toList();

        return labelsDTO;
    }

    public LabelDTO findById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label with id: " + id + " not found"));

        return labelMapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO labelCreateDTO) {
        var label = labelMapper.map(labelCreateDTO);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    public LabelDTO update(LabelUpdateDTO labelUpdateDTO, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label with id: " + id + " not found"));

        labelMapper.update(labelUpdateDTO, label);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    public void destroy(Long id) {
        labelRepository.deleteById(id);
    }

}
