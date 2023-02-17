package br.com.devantenor.clinivet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestController {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "teste@gmail.com", password = "123456", roles = "VETERINARIO")
	public void allTests() throws Exception {
		authenticateUser();
		findAllByClienteOrAnimalName();
		editCliente4();
	}

	public void authenticateUser() throws Exception {
		String endpoint = "/clientes";
		getMockGetRequestBody(endpoint);
	}

	public void findAllByClienteOrAnimalName() throws Exception {
		String endpoint = "/animais/filtro/nomeclienteanimal/carlos";
		getMockGetRequestBody(endpoint);
	}

	public void editCliente4() throws Exception {
		String endpoint = "/clientes/edit/4";
		String jsonContent = "{\"nome\":\"cleber\",\"telefone\":\"34998548774\"}";
		getMockPutRequestBody(endpoint, jsonContent);
	}

	private void getMockGetRequestBody(String endpoint) throws Exception {
		mockMvc.perform(get(endpoint))
				.andExpect(status().isOk());
	}

	private void getMockPutRequestBody(String endpoint, String jsonContent) throws Exception {
		mockMvc.perform(put(endpoint)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk());
	}
}