package github.com.miralhas.lecturizebackend.config.model_mapper;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(User.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
        modelMapper.typeMap(Lecture.class, LectureDTO.class).addMappings(mapper -> {
            mapper.map(Lecture::getTypeFormatted, LectureDTO::setType);
            mapper.map(Lecture::getStatusFormatted, LectureDTO::setStatus);
        });
        modelMapper.typeMap(Lecture.class, LectureSummaryDTO.class).addMappings(mapper -> {
            mapper.map(Lecture::getTypeFormatted, LectureSummaryDTO::setType);
            mapper.map(Lecture::getStatusFormatted, LectureSummaryDTO::setStatus);
        });
        return modelMapper;
    }

}
