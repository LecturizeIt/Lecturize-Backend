package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserMapper;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.repository.LectureRepository;
import github.com.miralhas.lecturizebackend.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

	private final AuthService authService;
	private final UserMapper userMapper;
	private final LectureRepository lectureRepository;
	private final LectureMapper lectureMapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getUser(JwtAuthenticationToken authToken) {
		User user = authService.findUserByEmailOrException(authToken.getName());
		return userMapper.toModel(user);
	}

	@GetMapping("/participating-lectures")
	@ResponseStatus(HttpStatus.OK)
	public List<LectureSummaryDTO> getUserParticipatingLectures(JwtAuthenticationToken authToken) {
		var user = authService.findUserByEmailOrException(authToken.getName());
		return lectureMapper.toSummaryCollectionModel(lectureRepository.findLecturesByParticipantId(user.getId()));
	}


}
