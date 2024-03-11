package hexlet.code.mapper;

import hexlet.code.DTO.taskDTO.TaskCreateDTO;
import hexlet.code.DTO.taskDTO.TaskDTO;
import hexlet.code.DTO.taskDTO.TaskUpdateDTO;
import hexlet.code.DTO.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public abstract class TaskMapper {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(source = "assignee_id", target = "assignee")
    @Mapping(source = "status", target = "taskStatus")
    public abstract Task map(TaskCreateDTO taskCreateDTO);

    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "assignee.id", target = "assignee_id")
    public abstract TaskDTO map(Task task);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(source = "assignee_id", target = "assignee")
    @Mapping(source = "status", target = "taskStatus")
    public abstract void update(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task task);

    //string status преобразовыввает в сущьность taskStatus для дальнейшего мапига
    public TaskStatus toEntity(String slug) {
        TaskStatus taskStatus = taskStatusRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("No such status"));

        return taskStatus;
    }
}






