package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.UserSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserMapper;
import github.com.miralhas.lecturizebackend.config.swagger.interfaces.SwaggerLectureParticipantsResource;
import github.com.miralhas.lecturizebackend.domain.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures/{id}")
public class LectureParticipantsResource implements SwaggerLectureParticipantsResource {

    private final LectureService lectureService;
    private final UserMapper userMapper;

    @Override
    @PutMapping("/participate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void participate(@PathVariable Long id, JwtAuthenticationToken authToken) {
        lectureService.confirmParticipant(id, authToken);
    }

    @Override
    @PutMapping("/unparticipate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unparticipate(@PathVariable Long id, JwtAuthenticationToken authToken) {
        lectureService.unconfirmParticipant(id, authToken);
    }

    @Override
    @GetMapping("/participants")
    @ResponseStatus(HttpStatus.OK)
    public List<UserSummaryDTO> getAllLectureParticipants(@PathVariable Long id) {
        var lectureParticipants = lectureService.getLectureOrException(id).getParticipants().stream().toList();
        return userMapper.toSummaryCollectionModel(lectureParticipants);
    }

}
