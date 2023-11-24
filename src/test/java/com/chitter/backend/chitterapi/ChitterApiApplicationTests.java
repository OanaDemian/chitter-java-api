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

import java.util.ArrayList;
import java.util.List;

import static com.chitter.backend.chitterapi.helpers.JsonFileReader.fileToObjectList;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ChitterApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	private List<Peep> peeps = fileToObjectList();
	private Peep testPeep = new Peep();


	@BeforeEach
	void clearCollection() {
		TestMongoConfig.clearCollection();
	}

	@Nested
	@DisplayName("Integrated GET Request Tests")
	class GetRequestTests {
		@Nested
		@DisplayName("GET peeps tests")
		class GetAll {

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
				public void repopulateCollection() {
					TestMongoConfig.repopulateCollection(peeps);
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
					TestMongoConfig.repopulateCollection(newPeeps);
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
					System.out.println(peeps.size());
					System.out.println(peeps);
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
}

