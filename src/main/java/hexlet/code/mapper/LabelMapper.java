package hexlet.code.mapper;

import hexlet.code.DTO.labelDTO.LabelCreateDTO;
import hexlet.code.DTO.labelDTO.LabelDTO;
import hexlet.code.DTO.labelDTO.LabelUpdateDTO;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

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
