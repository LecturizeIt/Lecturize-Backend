package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.UnknownHostException;

public interface SwaggerTestResource {

    @Operation(summary = "Get local host information", description = "Returns the local host's address and other related information as a string.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Local host information retrieved successfully"), @ApiResponse(responseCode = "500", description = "Internal server error due to host lookup failure")})
    String lecturizeIt() throws UnknownHostException;

}
