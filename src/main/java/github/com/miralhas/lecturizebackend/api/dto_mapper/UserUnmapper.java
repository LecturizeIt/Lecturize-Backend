package github.com.miralhas.lecturizebackend.api.dto_mapper;

import github.com.miralhas.lecturizebackend.api.dto.input.CreateUserInput;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUnmapper {

    private final ModelMapper modelMapper;

    public User toDomainObject(CreateUserInput createUserInput) {
        return modelMapper.map(createUserInput, User.class);
    }

}