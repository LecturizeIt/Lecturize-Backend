package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import github.com.miralhas.lecturizebackend.domain.service.LectureService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lectures")
public class LectureResource {

    private final LectureService lectureService;
    private final LectureMapper lectureMapper;
    private final LectureUnmapper lectureUnmapper;
    private final LectureRepository lectureRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LectureSummaryDTO> getAllLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        return lectureMapper.toSummaryCollectionModel(lectures);
    }

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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LectureDTO updateLecture(@RequestBody @Valid LectureInput lectureInput, @PathVariable Long id) {
        Lecture lecture = lectureService.update(lectureInput, id);
        return lectureMapper.toModel(lecture);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLecture(@PathVariable Long id) {
        lectureService.delete(id);
    }

}
