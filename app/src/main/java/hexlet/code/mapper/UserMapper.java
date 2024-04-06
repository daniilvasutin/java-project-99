package hexlet.code.mapper;

import hexlet.code.DTO.userDTO.UserCreateDTO;
import hexlet.code.DTO.userDTO.UserDTO;
import hexlet.code.DTO.userDTO.UserUpdateDTO;
import hexlet.code.model.User;
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

public abstract class UserMapper {
    public abstract User map(UserCreateDTO userDto);

    public abstract UserDTO map(User user);
    public abstract void update(UserUpdateDTO updateDTO, @MappingTarget User user);
}
