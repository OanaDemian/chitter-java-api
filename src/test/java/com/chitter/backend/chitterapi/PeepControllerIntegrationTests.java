package com.chitter.backend.chitterapi;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.chitter.backend.chitterapi.helpers.JsonFileReader.fileToPeepObjectList;
import static com.chitter.backend.chitterapi.helpers.JsonFileReader.fileToUserObjectList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PeepControllerIntegrationTests {
	@Autowired
	private MockMvc mockMvc;
	private List<Peep> peeps = fileToPeepObjectList();
	List <User> users = fileToUserObjectList();
	private String requestBody;



	@BeforeEach
	void clearPeepsCollection() {
		TestMongoConfig.clearPeepsCollection();
	}

	@Nested
	@DisplayName("Integrated GET Request Tests")
	class GetRequestTests {
		@Nested
		@DisplayName("GET peeps tests")
		class GetAllPeeps {

			@Test
			@DisplayName("Should return OK Http status code - regardless of how many peeps are found")
			void shouldReturnOKHttpStatusCode() throws Exception {
				mockMvc.perform(get("/peeps"))
						.andExpect(status().isOk());
			}

			@Test
			@DisplayName("Should return JSON - regardless of how many peeps are found")
			void shouldReturnJson() throws Exception {
				mockMvc.perform(get("/peeps"))
						.andExpect(content().contentType(MediaType.APPLICATION_JSON));
			}

			@Nested
			@DisplayName("When 1 or more peeps are found")
			class WhenPeepsFound {

				@BeforeEach
				public void repopulatePeepsCollection() {
					TestMongoConfig.repopulatePeepsCollection(peeps);
				}

				@Test
				@DisplayName("Should return the found peeps as JSON with correct number of peeps")
				public void shouldReturnFoundPeepsAsJSONWithRightNumberOfPeeps() throws Exception {
					mockMvc.perform(get("/peeps"))
							.andExpect(jsonPath("$[0].username", Matchers.is(peeps.get(1).getUsername())))
							.andExpect(jsonPath("$[0].content", Matchers.is(peeps.get(1).getContent())))
							.andExpect(jsonPath("$[0].date", Matchers.is(peeps.get(1).getDate())));
				}

				@Test
				@DisplayName("Should return a list of length 2")
				public void shouldReturnListOfLength2() throws Exception {
					mockMvc.perform(get("/peeps"))
							.andExpect(jsonPath("$", hasSize(2)));
				}

				@Test
				@DisplayName("Should return the peeps in reverse chronological order")
				public void shouldReturnListOfPeepsInReverseChronologicalOrder() throws Exception {
					List<Peep> newPeeps = new ArrayList<Peep>();
					User user = new User();
					user.set_id("64e0f63c5f8069dbfd030ae7");
					user.setName("Stacey");
					user.setUsername("Stacey1");
					Peep testPeep = new Peep(user, "Peep peep");
					newPeeps.add(testPeep);
					TestMongoConfig.repopulatePeepsCollection(newPeeps);
					mockMvc.perform(get("/peeps"))
							.andExpect(jsonPath("$[2].date", Matchers.is(peeps.get(0).getDate())))
							.andExpect(jsonPath("$[0].date", Matchers.is(testPeep.getDate())));
				}
			}
			@Nested
			@DisplayName("When peeps collection is empty")
			class NoPeepsFound {

				@Test
				@DisplayName("Should return an ArrayList with size 0")
				public void shouldReturnArrayListWithSize0 () throws Exception {
					mockMvc.perform(get("/peeps"))
							.andExpect(jsonPath("$", hasSize(0)));
				}
			}
		}
	}

	@Nested
	@DisplayName("Integrated POST Request Tests")
	class PostRequestTests {

		@Nested
		@DisplayName("Valid new peep tests")
		class ValidNewPeepTests {
			@BeforeEach
			public void repopulateUsersCollection() {
				TestMongoConfig.repopulateUsersCollection(users);
			}

			@BeforeEach
			public void makeRequestBody() {
				requestBody = "{\"content\": \"" + peeps.get(0).getContent() +
						"\",\"username\": \"" + users.get(0).getUsername() +
						"\"}";
			}
            @BeforeEach
			public void clearUsersCollection() {
				TestMongoConfig.clearUsersCollection();
			}

			@Test
			@DisplayName("Should return status 201 when a valid peep is submitted")
			public void shouldReturn201StatusWhenValidPeepSubmitted() throws Exception {
				TestMongoConfig.clearPeepsCollection();
				mockMvc.perform(MockMvcRequestBuilders
								.post("/peeps")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated());
			}

			@Test
			@DisplayName("Should return the valid peep submitted")
			public void shouldReturnTheValidPeepSubmitted() throws Exception {
				mockMvc.perform(MockMvcRequestBuilders
								.post("/peeps")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.content", is(peeps.get(0).getContent())))
						.andExpect(jsonPath("$.username", is(users.get(0).getUsername())))
						.andExpect(jsonPath("$._id", is(not(emptyString()))));
			}
		}

		@Nested
		@DisplayName("Invalid new peep tests")
		class InvalidNewPeepTests {

			@Test
			@DisplayName("Should return a 400 status when peep content is empty")
			public void shouldReturn400WhenEmptyPeepContent() throws Exception {
				requestBody = "{\"content\": \"\"" +
						"\",\"username\": \"" + peeps.get(0).getUsername() +
						"\"}";
				mockMvc.perform(MockMvcRequestBuilders
								.post("/peeps")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest());
			}
		}
	}
}
