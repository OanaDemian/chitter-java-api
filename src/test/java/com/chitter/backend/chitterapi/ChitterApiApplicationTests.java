package com.chitter.backend.chitterapi;

import com.chitter.backend.chitterapi.model.Peep;
import org.hamcrest.Matchers;
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

import java.util.ArrayList;
import java.util.List;

import static com.chitter.backend.chitterapi.helpers.JsonFileReader.fileToPeepObjectList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChitterApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private List<Peep> peeps = fileToPeepObjectList();
	private Peep testPeep = new Peep();
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
							.andExpect(jsonPath("$[0].peepContent", Matchers.is(peeps.get(1).getPeepContent())))
							.andExpect(jsonPath("$[0].dateCreated", Matchers.is(peeps.get(1).getDateCreated())));
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
					String newDate = "2056-02-06T20:20:13Z";
					List<Peep> newPeeps = new ArrayList<Peep>();
					Peep testPeep1 = new Peep();
					testPeep1.setPeepContent("Peep peep");
					testPeep1.setDateCreated(newDate);
					testPeep1.setName("Stacey");
					testPeep1.setUsername("Stacey1");
					testPeep1.setUserId("64e0f63c5f8069dbfd030ae7");
					testPeep1.set_id("5c9e51c24c6ee53ff09d5d03");
					newPeeps.add(testPeep1);
					TestMongoConfig.repopulatePeepsCollection(newPeeps);
					mockMvc.perform(get("/peeps"))
							.andExpect(jsonPath("$[2].dateCreated", Matchers.is(peeps.get(0).getDateCreated())))
							.andExpect(jsonPath("$[0].dateCreated", Matchers.is(newDate)));
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
			public void makeRequestBody() {
				requestBody = "{\"peepContent\": \"" + peeps.get(0).getPeepContent() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() +
						"\",\"username\": \"" + peeps.get(0).getUsername() +
						"\",\"name\": \"" + peeps.get(0).getName() +
						"\",\"userId\": \"" + peeps.get(0).getUserId() +
						"\"}";
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
						.andExpect(jsonPath("$.peepContent", is(peeps.get(0).getPeepContent())))
						.andExpect(jsonPath("$.dateCreated", is(peeps.get(0).getDateCreated())))
						.andExpect(jsonPath("$.username", is(peeps.get(0).getUsername())))
						.andExpect(jsonPath("$._id", is(not(emptyString()))));
			}
		}

		@Nested
		@DisplayName("Invalid new peep tests")
		class InvalidNewPeepTests {

			@Test
			@DisplayName("Should return a 400 status when peep content is empty")
			public void shouldReturn400WhenEmptyPeepContent() throws Exception {
				requestBody = "{\"peepContent\": \"\"" +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() +
						"\",\"username\": \"" + peeps.get(0).getUsername() +
						"\",\"name\": \"" + peeps.get(0).getName() +
						"\",\"userId\": \"" + peeps.get(0).getUserId() +
						"\"}";
				mockMvc.perform(MockMvcRequestBuilders
								.post("/peeps")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest());
			}

			@Test
			@DisplayName("Should return a 400 status when date created is empty")
			public void shouldReturn400WhenEmptyDateCreated() throws Exception {
				requestBody = "{\"peepContent\": \"" + peeps.get(0).getPeepContent() +
						"\",\"dateCreated\": \"\"" +
						"\",\"username\": \"" + peeps.get(0).getUsername() +
						"\",\"name\": \"" + peeps.get(0).getName() +
						"\",\"userId\": \"" + peeps.get(0).getUserId() +
						"\"}";
				System.out.println(requestBody);
				mockMvc.perform(MockMvcRequestBuilders
								.post("/peeps")
								.content(requestBody)
								.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest());
			}

			@Test
			@DisplayName("Should return a 400 status when userId is empty")
			public void shouldReturn400WhenEmptyUserId() throws Exception {
				requestBody = "{\"peepContent\": \"" + peeps.get(0).getPeepContent() +
						"\",\"dateCreated\": \"" + peeps.get(0).getDateCreated() +
						"\",\"username\": \"" + peeps.get(0).getUsername() +
						"\",\"name\": \"" + peeps.get(0).getName() +
						"\",\"userId\": \"\"" +
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
