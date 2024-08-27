package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDTO toModel(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        var roles = user.getRoles().stream().map(r -> r.getName().name()).toList();
        userDTO.setRoles(roles);
        return userDTO;
    }

    public List<UserDTO> toCollectionModel(List<User> users) {
        return users.stream().map(this::toModel).toList();
    }

}