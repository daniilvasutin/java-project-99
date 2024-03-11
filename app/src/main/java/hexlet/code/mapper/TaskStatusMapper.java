package hexlet.code.mapper;

import hexlet.code.DTO.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.model.TaskStatus;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class TaskStatusMapper {
    public abstract TaskStatus map(TaskStatusCreateDTO taskStatusCreateDTO);
    public abstract TaskStatusDTO map(TaskStatus taskStatus);
    public abstract void update(TaskStatusUpdateDTO taskStatusUpdateDTO, @MappingTarget TaskStatus taskStatus);
}
