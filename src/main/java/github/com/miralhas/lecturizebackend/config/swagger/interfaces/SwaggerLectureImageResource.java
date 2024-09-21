package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.LectureImageDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LectureImageInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;

public interface SwaggerLectureImageResource {

    @Operation(summary = "Get lecture image details", description = "Retrieves the JSON details of a lecture image by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lecture image details retrieved successfully"), @ApiResponse(responseCode = "404", description = "Lecture image not found")})
    LectureImageDTO getLectureImageJson(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id);

    @Operation(summary = "Get lecture image file", description = "Retrieves the lecture image file by its ID. Supports different media types based on the 'Accept' header.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lecture image file retrieved successfully"), @ApiResponse(responseCode = "404", description = "Lecture image not found"), @ApiResponse(responseCode = "406", description = "Requested media type not acceptable")})
    ResponseEntity<InputStreamResource> getLectureImage(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id, @Parameter(description = "Media type to accept", required = true) @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Update lecture image", description = "Updates the lecture image associated with the given lecture ID. Requires multipart file input.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lecture image updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid input or file format"), @ApiResponse(responseCode = "404", description = "Lecture or image not found"), @ApiResponse(responseCode = "403", description = "User is not authorized to update this lecture image")})
    LectureImageDTO updateLectureImage(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id, @Parameter(description = "Lecture image details for update", required = true) @Valid LectureImageInput lectureImageInput, @Parameter(hidden = true) JwtAuthenticationToken authToken) throws IOException;

    @Operation(summary = "Delete lecture image", description = "Deletes the lecture image associated with the given lecture ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Lecture image deleted successfully"), @ApiResponse(responseCode = "404", description = "Lecture or image not found"), @ApiResponse(responseCode = "403", description = "User is not authorized to delete this lecture image")})
    void deleteLectureImage(@Parameter(description = "ID of the lecture", required = true) @PathVariable Long id, @Parameter(hidden = true) JwtAuthenticationToken authToken);

}
