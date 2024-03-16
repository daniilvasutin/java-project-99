package hexlet.code.mapper;

import hexlet.code.DTO.labelDTO.LabelCreateDTO;
import hexlet.code.DTO.labelDTO.LabelDTO;
import hexlet.code.DTO.labelDTO.LabelUpdateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class LabelMapper {
    public abstract Label map(LabelCreateDTO labelCreateDTO);
    public abstract LabelDTO map(Label label);
    public abstract void update(LabelUpdateDTO labelUpdateDTO, @MappingTarget Label label);
}
