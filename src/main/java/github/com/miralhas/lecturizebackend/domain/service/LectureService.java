package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.LectureNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
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

    public void validateLecture(Lecture lecture) {
        lecture.onlineValidations();
        lecture.presentialValidations();
    }
}
