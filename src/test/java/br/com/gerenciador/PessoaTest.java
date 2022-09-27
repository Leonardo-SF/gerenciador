package br.com.gerenciador;

import br.com.gerenciador.pessoa.model.Pessoa;
import br.com.gerenciador.pessoa.model.PessoaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GerenciadorApplication.class)
@EnableAutoConfiguration
public class PessoaTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void validarIdentificadorInvalido() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdentificador("xxx.xxx.xxx-xx");
        when(pessoaService.save(pessoa)).thenReturn(pessoa);
        this.mockMvc.perform(post("/api/v1/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pessoa))).andExpect(status().isBadRequest());

        pessoa.setIdentificador("111");
        when(pessoaService.save(pessoa)).thenReturn(pessoa);
        this.mockMvc.perform(post("/api/v1/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pessoa))).andExpect(status().isBadRequest());
    }

    @Test
    public void validarIdentificadorValido() throws Exception {
        Pessoa pessoa = new Pessoa();

        pessoa.setIdentificador("111.111.111-11");
        when(pessoaService.save(pessoa)).thenReturn(pessoa);
        this.mockMvc.perform(post("/api/v1/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pessoa))).andExpect(status().isOk());

        pessoa.setIdentificador("11.111.111/0001-11");
        when(pessoaService.save(pessoa)).thenReturn(pessoa);
        this.mockMvc.perform(post("/api/v1/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pessoa))).andExpect(status().isOk());
    }

    @Test
    public void validarIdentificadorJaCadastrado() throws Exception {
        final String identificador = "11111111111";
        Pessoa pessoa = new Pessoa();
        pessoa.setIdentificador(identificador);

        when(pessoaService.findByIdentificador(identificador)).thenReturn(Optional.of(pessoa));
        when(pessoaService.save(pessoa)).thenReturn(pessoa);

        this.mockMvc.perform(post("/api/v1/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(pessoa))).andExpect(status().isBadRequest());
    }

    private String toJson(Pessoa pessoa) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pessoa);
    }
}
