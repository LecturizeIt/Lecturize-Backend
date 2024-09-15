package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureUnmapper;
import github.com.miralhas.lecturizebackend.domain.exception.LectureNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final AuthService authService;
    private final LectureUnmapper lectureUnmapper;
    private final CategoryTagService categoryTagService;

    public Lecture getLectureOrException(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException(id));
    }


    @Transactional
    public Lecture create(Lecture lecture, JwtAuthenticationToken authToken) {
        validateLecture(lecture);
        validateTags(lecture);
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
        validateTags(lecture);
        return lectureRepository.save(lecture);
    }


    @Transactional
    public void delete(Long id, JwtAuthenticationToken authToken) {
        Lecture lecture = getLectureOrException(id);
        User user = authService.findUserByEmailOrException(authToken.getName());
        validateOrganizer(user, lecture);
        lectureRepository.delete(lecture);
    }


    @Transactional
    public void confirmParticipant(Long id, JwtAuthenticationToken authToken) {
        var lecture = getLectureOrException(id);
        var user = authService.findUserByEmailOrException(authToken.getName());
        lecture.getParticipants().add(user);
        lectureRepository.save(lecture);
    }


    @Transactional
    public void unconfirmParticipant(Long id, JwtAuthenticationToken authToken) {
        var lecture = getLectureOrException(id);
        var user = authService.findUserByEmailOrException(authToken.getName());
        lecture.getParticipants().remove(user);
        lectureRepository.save(lecture);
    }


    public void validateOrganizer(User user, Lecture lecture) {
        if (user.isAdmin() || Objects.equals(user, lecture.getOrganizer())) return;
        throw new AccessDeniedException("Acesso negado. " +
                "Apenas o organizador ou usuários autorizados podem fazer alterações a esta palestra.");
    }

    private void validateTags(Lecture lecture) {
        var tags = lecture.getTags().stream()
                .map(t -> categoryTagService.getTagOrException(t.getId()))
                .collect(Collectors.toSet());
        lecture.setTags(tags);
    }

    private void validateLecture(Lecture lecture) {
        lecture.validateType();
        lecture.validateDateRange();
    }
}
