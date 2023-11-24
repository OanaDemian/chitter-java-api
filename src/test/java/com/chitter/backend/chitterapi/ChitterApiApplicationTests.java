package com.chitter.backend.chitterapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChitterApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Nested
	@DisplayName("GET peeps tests")
	class GetAll {

		@Test
		@DisplayName("Should return OK Http status code - regardless of how many peeps are found")
		void shouldReturnOKHttpStatusCode() throws Exception {
			mockMvc.perform(get("/peeps"))
					.andExpect(status().isOk());
		}
	}
}
