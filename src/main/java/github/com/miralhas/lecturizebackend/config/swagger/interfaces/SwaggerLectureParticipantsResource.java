package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.UserSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SwaggerLectureParticipantsResource {

    @Operation(summary = "Confirm participation in a lecture", description = "Confirms the participation of the authenticated user in the specified lecture.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Participation confirmed successfully"), @ApiResponse(responseCode = "404", description = "Lecture not found"), @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")})
    void participate(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id, @Parameter(hidden = true) JwtAuthenticationToken authToken);

    @Operation(summary = "Unconfirm participation in a lecture", description = "Unconfirms the participation of the authenticated user in the specified lecture.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Participation unconfirmed successfully"), @ApiResponse(responseCode = "404", description = "Lecture not found"), @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated")})
    void unparticipate(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id, @Parameter(hidden = true) JwtAuthenticationToken authToken);

    @Operation(summary = "Get all participants of a lecture", description = "Retrieves a list of all participants of the specified lecture.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of lecture participants retrieved successfully"), @ApiResponse(responseCode = "404", description = "Lecture not found")})
    List<UserSummaryDTO> getAllLectureParticipants(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id);
    
}
