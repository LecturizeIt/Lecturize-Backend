package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.LectureDTO;
import github.com.miralhas.lecturizebackend.api.dto.LectureSummaryDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SwaggerLectureResource {

    @Operation(summary = "Get all lectures", description = "Retrieves a list of all lectures. Optionally filter by user.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of lectures retrieved successfully")})
    List<LectureSummaryDTO> getAllLectures(@Parameter(description = "Filter lectures by user", example = "john.doe@example.com") @RequestParam(required = false) String user);

    @Operation(summary = "Get lecture by ID", description = "Retrieves the details of a lecture by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lecture details retrieved successfully"), @ApiResponse(responseCode = "404", description = "Lecture not found")})
    LectureDTO getLecture(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id);

    @Operation(summary = "Create a new lecture", description = "Creates a new lecture with the provided details.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Lecture created successfully"), @ApiResponse(responseCode = "400", description = "Invalid input, lecture creation failed")})
    LectureDTO createLecture(@Parameter(description = "Lecture details for creation", required = true) @Valid @RequestBody LectureInput lectureInput, @Parameter(hidden = true) JwtAuthenticationToken authToken);

    @Operation(summary = "Update an existing lecture", description = "Updates the details of an existing lecture by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lecture updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid input or lecture update failed"), @ApiResponse(responseCode = "404", description = "Lecture not found")})
    LectureDTO updateLecture(@Parameter(description = "Lecture details for update", required = true) @Valid @RequestBody LectureInput lectureInput, @Parameter(description = "ID of the lecture to be updated", required = true) @PathVariable Long id, @Parameter(hidden = true) JwtAuthenticationToken authToken);

    @Operation(summary = "Delete a lecture", description = "Deletes an existing lecture by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Lecture deleted successfully"), @ApiResponse(responseCode = "404", description = "Lecture not found")})
    void deleteLecture(@Parameter(description = "ID of the lecture to be deleted", required = true) @PathVariable Long id, @Parameter(hidden = true) JwtAuthenticationToken authToken);

}
