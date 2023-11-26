package com.chitter.backend.chitterapi;

import com.chitter.backend.chitterapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.chitter.backend.chitterapi.helpers.JsonFileReader.fileToUserObjectList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    private List<User> users = fileToUserObjectList();
    private User testUser = new User();
    private String requestBody;



    @BeforeEach
    void clearUsersCollection() {
        TestMongoConfig.clearUsersCollection();
    }

    @Nested
    @DisplayName("Integrated POST Request Tests")
    class PostRequestTests {

        @Nested
        @DisplayName("Valid new user tests")
        class ValidNewUserTests {

            @BeforeEach
            public void makeRequestBody() {
                requestBody = "{\"name\": \"" + users.get(0).getName() +
                        "\",\"email\": \"" + users.get(0).getEmail() +
                        "\",\"username\": \"" + users.get(0).getUsername() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
            }

            @Test
            @DisplayName("Should return status 201 when a valid user is submitted")
            public void shouldReturn201StatusWhenValidUserSubmitted() throws Exception {
                TestMongoConfig.clearUsersCollection();
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("Should return the valid user submitted")
            public void shouldReturnTheValidUserSubmitted() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is(users.get(0).getName())))
                        .andExpect(jsonPath("$.email", is(users.get(0).getEmail())))
                        .andExpect(jsonPath("$.username", is(users.get(0).getUsername())))
                        .andExpect(jsonPath("$.password", is(users.get(0).getPassword())))
                        .andExpect(jsonPath("$._id", is(not(emptyString()))));
            }
        }

        @Nested
        @DisplayName("Invalid new user tests")
        class InvalidNewUserTests {

            @Test
            @DisplayName("Should return a 400 status when user password is empty")
            public void shouldReturn400WhenEmptyPassword() throws Exception {
                requestBody = "{\"email\": \"" + users.get(0).getEmail() +
                        "\",\"password\": \"\"" +
                        "\",\"username\": \"" + users.get(0).getUsername() +
                        "\",\"name\": \"" + users.get(0).getName() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Should return a 400 status when email is empty")
            public void shouldReturn400WhenEmptyEmail() throws Exception {
                requestBody = "{\"email\": \"\"" +
                        "\",\"username\": \"" + users.get(0).getUsername() +
                        "\",\"name\": \"" + users.get(0).getName() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                System.out.println(requestBody);
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Should return a 400 status when user's name is empty")
            public void shouldReturn400WhenEmptyName() throws Exception {
                requestBody = "{\"email\": \"\"" + users.get(0).getEmail() +
                        "\",\"username\": \"" + users.get(0).getUsername() +
                        "\",\"name\":  \"\"" +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Should return a 400 status when user's username is empty")
            public void shouldReturn400WhenEmptyUsername() throws Exception {
                requestBody = "{\"email\": \"\"" + users.get(0).getEmail() +
                        "\",\"username\": \"\""  +
                        "\",\"name\":  \"\"" + users.get(0).getName() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Should return a 400 status when a taken username is submitted")
            public void shouldReturn400WhenTakenUsername() throws Exception {
                TestMongoConfig.repopulateUsersCollection(users);
                requestBody = "{\"name\": \"" + users.get(0).getName() +
                        "\",\"email\": \"" + users.get(0).getEmail() +
                        "\",\"username\": \"" + users.get(0).getUsername() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signup")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("Sign in tests")
        class SignInTests {

            @BeforeEach
            public void repopulateUsersCollection() {TestMongoConfig.repopulateUsersCollection(users);}

            @Test
            @DisplayName("Returns user details and status 200 when valid credentials are submitted")
            public void returnsUserDetailsAndStatus200WhenValidCredentialsSubmitted() throws Exception {
                requestBody = "{\"username\": \"" + users.get(0).getUsername() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signin")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Returns user details and status 401 when invalid credentials are submitted")
            public void returnsStatus401WhenInvalidCredentialsSubmitted() throws Exception {
                requestBody = "{\"username\": \"" + users.get(1).getUsername() +
                        "\",\"password\": \"" + users.get(0).getPassword() +
                        "\"}";
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/signin")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError());
            }
        }
        }
}





