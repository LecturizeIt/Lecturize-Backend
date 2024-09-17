package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.input.TagInput;
import github.com.miralhas.lecturizebackend.domain.model.lecture.CategoryTag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SwaggerTagResource {

    @Operation(summary = "Get all tags", description = "Retrieves a list of all category tags.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of category tags retrieved successfully")})
    List<CategoryTag> getAllTags();

    @Operation(summary = "Get tag by ID", description = "Retrieves a specific category tag by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Category tag details retrieved successfully"), @ApiResponse(responseCode = "404", description = "Category tag not found")})
    CategoryTag getTagById(@Parameter(description = "ID of the category tag", required = true) @PathVariable Long id);

    @Operation(summary = "Create a new tag", description = "Creates a new category tag with the provided details.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Category tag created successfully"), @ApiResponse(responseCode = "400", description = "Invalid input, tag creation failed")})
    CategoryTag createTag(@Parameter(description = "Details of the tag to be created", required = true) @Valid @RequestBody TagInput tagInput);

    @Operation(summary = "Update an existing tag", description = "Updates the details of an existing category tag by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Category tag updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid input, tag update failed"), @ApiResponse(responseCode = "404", description = "Category tag not found")})
    CategoryTag updateTag(@Parameter(description = "Updated details of the tag", required = true) @Valid @RequestBody TagInput tagInput, @Parameter(description = "ID of the category tag to be updated", required = true) @PathVariable Long id);

    @Operation(summary = "Delete a tag", description = "Deletes an existing category tag by its ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Category tag deleted successfully"), @ApiResponse(responseCode = "404", description = "Category tag not found")})
    void deleteTag(@Parameter(description = "ID of the category tag to be deleted", required = true) @PathVariable Long id);
    
}
