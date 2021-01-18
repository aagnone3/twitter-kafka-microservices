package com.aagnone3.springkafka.producer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.aagnone3.springkafka.producer.domain.SourceData;
import com.aagnone3.springkafka.producer.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class SourceDataProducerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private DataService mockDataService;

	@Test
	void contextLoads() {
		assert(true);
	}

	@Test
	void createsData() throws Exception {
		// arrange
		SourceData testData = new SourceData(Long.valueOf(1), "description", true);
		when(mockDataService.send(any(SourceData.class))).thenReturn(null);

		// act
		ResultActions result = mockMvc.perform(
			MockMvcRequestBuilders.post("/data")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(testData)));

		// assert
		result
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testData.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testData.getDescription()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(testData.getCompleted()));
	}

}
