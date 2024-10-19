package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.domain.service.LectureMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures/{id}")
public class LectureMetricsResource {

    private final LectureMetricsService lectureMetricsService;

    @PutMapping("/visit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void visitLecture(@PathVariable Long id) {
        lectureMetricsService.updateLectureTimesVisited(id);
    }

    @PutMapping("/share")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void shareLecture(@PathVariable Long id) {
        lectureMetricsService.updateLectureTimesShared(id);
    }
}
