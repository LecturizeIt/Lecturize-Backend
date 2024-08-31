package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.domain.exception.LectureNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final AuthService authService;
    private final LectureUnmapper lectureUnmapper;
    private final MessageSource messageSource;

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
    public Lecture update(@Valid LectureInput lectureInput, Long id, JwtAuthenticationToken authToken) {
        var lecture = getLectureOrException(id);
        var user = authService.findUserByEmailOrException(authToken.getName());
        validateOrganizer(user, lecture);
        lectureUnmapper.copyToDomainObject(lectureInput, lecture);
        validateLecture(lecture);
        return lectureRepository.save(lecture);
    }


    @Transactional
    public void delete(Long id, JwtAuthenticationToken authToken) {
        Lecture lecture = getLectureOrException(id);
        User user = authService.findUserByEmailOrException(authToken.getName());
        validateOrganizer(user, lecture);
        lectureRepository.delete(lecture);
    }


    private void validateOrganizer(User user, Lecture lecture) {
        if (authService.isAdmin(user) || Objects.equals(user, lecture.getOrganizer())) return;
        throw new AccessDeniedException(messageSource.getMessage(
                "AbstractAccessDecisionManager.accessDenied", new Object[]{}, LocaleContextHolder.getLocale()
        ));
    }


    private void validateLecture(Lecture lecture) {
        lecture.onlineValidations();
        lecture.presentialValidations();
        lecture.validateDateRange();
    }
}
