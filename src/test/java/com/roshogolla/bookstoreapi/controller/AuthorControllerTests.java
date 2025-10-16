package com.roshogolla.bookstoreapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshogolla.bookstoreapi.dto.author.AuthorCreateDTO;
import com.roshogolla.bookstoreapi.dto.author.AuthorUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthorService authorService;

    @Test
    public void testCreateAuthor() throws Exception{
        //Input for JSON
        AuthorCreateDTO authorCreateDTO = new AuthorCreateDTO();
        authorCreateDTO.setName("Polin");
        authorCreateDTO.setBio("I love animals");

        //Output
        Author savedAuthor = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        //Define mock behavior
        when(authorService.addAuthor(ArgumentMatchers.any(Author.class))).thenReturn(savedAuthor);

        //Performs POST Request
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Polin"))
                .andExpect(jsonPath("$.bio").value("I love animals"))
                .andExpect(jsonPath("$.bookTitles").isArray());
    }

    @Test
    public void testGetAuthorById() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/authors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Polin"))
                .andExpect(jsonPath("$.bio").value("I love animals"))
                .andExpect(jsonPath("$.bookTitles").isArray());
    }

    @Test
    public void testGetAuthorByName () throws Exception {
        Author author = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        when(authorService.searchAuthorsByName("Polin")).thenReturn(List.of(author));

        mockMvc.perform(get("/authors/search/by-name")
                        .param("name", "Polin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Polin"))
                .andExpect(jsonPath("$[0].bio").value("I love animals"))
                .andExpect(jsonPath("$[0].bookTitles").isArray());
    }

    @Test
    public void testSearchAuthorByKeyword() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        when(authorService.searchAuthorsByKeyword("polin")).thenReturn(List.of(author));

        mockMvc.perform(get("/authors/search/by-keyword")
                        .param("keyword", "polin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Polin"))
                .andExpect(jsonPath("$[0].bio").value("I love animals"))
                .andExpect(jsonPath("$[0].bookTitles").isArray());
    }

    @Test
    public void testGetAllAuthor() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        when(authorService.getAllAuthors()).thenReturn(List.of(author));

        mockMvc.perform(get("/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Polin"))
                .andExpect(jsonPath("$[0].bio").value("I love animals"))
                .andExpect(jsonPath("$[0].bookTitles").isArray());
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").bio("I love animals").books(List.of()).build();

        AuthorUpdateDTO authorUpdateDTO = new AuthorUpdateDTO();
        authorUpdateDTO.setId(1L);
        authorUpdateDTO.setName("Rupa");
        authorUpdateDTO.setBio("I love animals and coding");

        Author updatedAuthor = Author.builder().id(1L).name("Rupa").bio("I love animals and coding").books(List.of()).build();

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        when(authorService.updateAuthor(1L, author)).thenReturn(updatedAuthor);

        mockMvc.perform(put("/authors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Rupa"))
                .andExpect(jsonPath("$.bio").value("I love animals and coding"))
                .andExpect(jsonPath("$.bookTitles").isArray());
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        //Since it's return nothing so no need to create a return with builder(). doNothing() has to be here
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/authors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Author deleted successfully"));

        verify(authorService, times(1)).deleteAuthor(1L);

    }
}

































