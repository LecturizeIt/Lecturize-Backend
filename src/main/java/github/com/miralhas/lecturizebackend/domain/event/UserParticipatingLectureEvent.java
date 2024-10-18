package github.com.miralhas.lecturizebackend.domain.event;

import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.model.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserParticipatingLectureEvent {
    private User user;
    private Lecture lecture;
}
