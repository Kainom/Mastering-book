package kainom.migration.migrations_liquibase.controller;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kainom.com.testing.config.security.SecurityConfig;
import kainom.com.testing.controllers.AuthorController;
import kainom.com.testing.model.Author;
import kainom.com.testing.model.Publisher;
import kainom.com.testing.service.AuthorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;

@WebMvcTest(AuthorController.class)
@Import(SecurityConfig.class)
public class AuthorControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private JwtDecoder jwtDecoder;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetAuthors() throws Exception {
        Author author = Author.builder().id(1L).name("Author 1").build();
        Author author2 = Author.builder().id(2L).name("Author 2").build();

        when(authorService.getAllAuthors()).thenReturn(List.of(author, author2));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCreateAuthor() throws Exception {
        Author author = Author.builder().id(1l).biography("Not").name("Kano").publisher(Publisher.builder().address("Nunca ser amado").name("Um preço que eu pago").id(1l).build()).build();
        when(authorService.createAuthor(author)).thenReturn(author);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andDo(print()) 
                .andExpect(status().isCreated());
    }

      @ParameterizedTest
    @MethodSource("provideRolesAndExpectedStatusForAddAuthor")
    void testAddAuthorWithDifferentRoles(String role, HttpStatus expectedStatus) throws Exception {
        Author author = Author.builder().build();
        author.setName("Author Name");

        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/authors")
                        .with(user("testUser").roles(role.replace("ROLE_", "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andDo(print())
                .andExpect(status().is(expectedStatus.value()));
    }
}