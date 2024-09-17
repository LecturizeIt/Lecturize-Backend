package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.config.swagger.interfaces.SwaggerLectureResource;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import github.com.miralhas.lecturizebackend.domain.service.LectureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lectures")
public class LectureResource implements SwaggerLectureResource {

    private final LectureService lectureService;
    private final LectureMapper lectureMapper;
    private final LectureUnmapper lectureUnmapper;
    private final LectureRepository lectureRepository;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LectureSummaryDTO> getAllLectures(@RequestParam(required = false) String user) {
        var lectures = hasText(user) ? lectureRepository.findAllUserLectures(user) : lectureRepository.findAll();
        return lectureMapper.toSummaryCollectionModel(lectures);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LectureDTO getLecture(@PathVariable Long id) {
        Lecture lecture = lectureService.getLectureOrException(id);
        return lectureMapper.toModel(lecture);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LectureDTO createLecture(@RequestBody @Valid LectureInput lectureInput, JwtAuthenticationToken authToken) {
        Lecture lecture = lectureUnmapper.toDomainObject(lectureInput);
        lecture = lectureService.create(lecture, authToken);
        return lectureMapper.toModel(lecture);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LectureDTO updateLecture(@RequestBody @Valid LectureInput lectureInput, @PathVariable Long id, JwtAuthenticationToken authToken) {
        Lecture lecture = lectureService.update(lectureInput, id, authToken);
        return lectureMapper.toModel(lecture);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLecture(@PathVariable Long id, JwtAuthenticationToken authToken) {
        lectureService.delete(id, authToken);
    }

}
