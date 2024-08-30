package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import github.com.miralhas.lecturizebackend.domain.service.LectureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lectures")
public class LectureResource {

    private final LectureService lectureService;
    private final LectureMapper lectureMapper;
    private final LectureUnmapper lectureUnmapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LectureDTO getLecture(@PathVariable Long id) {
        Lecture lecture = lectureService.getLectureOrException(id);
        return lectureMapper.toModel(lecture);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LectureDTO createLecture(@RequestBody @Valid LectureInput lectureInput, JwtAuthenticationToken authToken) {
        Lecture lecture = lectureUnmapper.toDomainObject(lectureInput);
        lecture = lectureService.create(lecture, authToken);
        return lectureMapper.toModel(lecture);
    }

}
