
package com.spring.mvc;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.chapter_05.controller.SpitterController;
import com.spring.chapter_05.entity.Spitter;
import com.spring.chapter_05.repository.SpitterRepository;

public class SpitterControllerTest {
	// @Test
	// public void shouldShowRegistration() throws Exception {
	// SpitterController controller = new SpitterController();
	// MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	// mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
	// .andExpect(MockMvcResultMatchers.view().name("registerForm"));
	// }

	@Test
	public void shouldProcessRegistration() throws Exception {
		// 构建Repository
		SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
		Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jack@gmail.com");
		Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jack@gmail.com");

		Mockito.when(mockRepository.save(unsaved)).thenReturn(saved);

		SpitterController controller = new SpitterController(mockRepository);
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(post("/spitter/register").param("firstName", "Jack").param("lastName", "Bauer")
				.param("username", "jbauer").param("password", "24hours").param("email", "jack@gmail.com"))
				.andExpect(redirectedUrl("/spitter/jbauer"));
		verify(mockRepository, atLeastOnce()).save(unsaved);
	}
}
