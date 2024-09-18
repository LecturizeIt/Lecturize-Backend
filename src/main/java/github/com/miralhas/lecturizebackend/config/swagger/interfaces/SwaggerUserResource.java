package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface SwaggerUserResource {

    @Operation(summary = "Get user details", description = "Retrieves the details of the currently authenticated user.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User details retrieved successfully"), @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")})
    UserDTO getUser(@Parameter(hidden = true) JwtAuthenticationToken authToken);

    @Operation(summary = "Get lectures the user is participating in", description = "Retrieves a list of lectures that the currently authenticated user is participating in.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of participating lectures retrieved successfully"), @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")})
    List<LectureSummaryDTO> getUserParticipatingLectures(@Parameter(hidden = true) JwtAuthenticationToken authToken);

}
