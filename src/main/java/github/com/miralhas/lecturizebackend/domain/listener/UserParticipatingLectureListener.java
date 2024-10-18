package github.com.miralhas.lecturizebackend.domain.listener;

import github.com.miralhas.lecturizebackend.domain.event.UserParticipatingLectureEvent;
import github.com.miralhas.lecturizebackend.domain.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

import static github.com.miralhas.lecturizebackend.domain.service.SendEmailService.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserParticipatingLectureListener {

    private final SendEmailService sendEmailService;

    @EventListener
    public void onUserParticipatingLecture(UserParticipatingLectureEvent event) {
        var user = event.getUser();
        var lecture = event.getLecture();

        var date = Date.from(lecture.getStartsAt().toInstant());
        var dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        var timeFormatter = new SimpleDateFormat("H:mm a");

        var lectureStartDate = dateFormatter.format(date);
        var lectureStartTime = timeFormatter.format(date);

        log.info("SENDING EMAIL TO USER: %s".formatted(user.getEmail()));

        var message = Message.builder()
                .recipient(user.getEmail())
                .subject("Confirmação de Inscrição - Palestra %s".formatted(lecture.getTitle()))
                .body("sended-user-participating-lecture.html")
                .model("user", user)
                .model("lecture", lecture)
                .model("startsAtDate", lectureStartDate)
                .model("startsAtTime", lectureStartTime)
                .build();
        sendEmailService.send(message);

        log.info("EMAIL SENT TO USER: %s".formatted(user.getEmail()));
    }

}
