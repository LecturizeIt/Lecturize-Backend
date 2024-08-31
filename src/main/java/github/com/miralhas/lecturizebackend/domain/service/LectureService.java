package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.domain.exception.LectureNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final AuthService authService;
    private final LectureUnmapper lectureUnmapper;

    public Lecture getLectureOrException(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException(id));
    }

    @Transactional
    public Lecture create(Lecture lecture, JwtAuthenticationToken authToken) {
        validateLecture(lecture);
        var user = authService.findUserByEmailOrException(authToken.getName());
        lecture.setOrganizer(user);
        lecture = lectureRepository.save(lecture);
        return lecture;
    }

    @Transactional
    public Lecture update(@Valid LectureInput lectureInput, Long id) {
        var lecture = getLectureOrException(id);
        lectureUnmapper.copyToDomainObject(lectureInput, lecture);
        validateLecture(lecture);
        return lectureRepository.save(lecture);
    }

    @Transactional
    public void delete(Long id) {
        lectureRepository.delete(getLectureOrException(id));
        lectureRepository.flush();
    }

    private void validateLecture(Lecture lecture) {
        lecture.onlineValidations();
        lecture.presentialValidations();
    }
}
