package github.com.miralhas.lecturizebackend.config.swagger.interfaces;

import github.com.miralhas.lecturizebackend.api.dto.LoginDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.CreateUserInput;
import github.com.miralhas.lecturizebackend.api.dto.input.LoginInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

public interface SwaggerAuthResource {

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided information. Returns the created user details.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created successfully"), @ApiResponse(responseCode = "400", description = "Invalid input, user creation failed")})
    UserDTO register(@Parameter(description = "User details for registration", required = true) @RequestBody CreateUserInput createUserInput);

    @Operation(summary = "Login a user", description = "Authenticates a user with the provided credentials and returns a JWT token for access.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"), @ApiResponse(responseCode = "401", description = "Invalid credentials, login failed")})
    LoginDTO login(@Parameter(description = "User credentials for login", required = true) @RequestBody LoginInput loginInput);

}
