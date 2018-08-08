package com.thoughtworks.training.wukun.todoservice.controller;

import com.google.common.collect.ImmutableList;
import com.thoughtworks.training.wukun.todoservice.dto.User;
import com.thoughtworks.training.wukun.todoservice.model.ToDo;
import com.thoughtworks.training.wukun.todoservice.repository.ToDoRepository;
import com.thoughtworks.training.wukun.todoservice.service.ToDoService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ToDoAPITest {

    private final int userId = 0;

    private final int todoId = 1;

    private final ToDo todoFixture = new ToDo(todoId, "foo", false, new Date(), Collections.emptyList(), userId, "", false);

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ToDoRepository toDoRepository;


    @Autowired
    private ToDoService toDoService;

    private UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            new User(userId, "fakeUser"), null, Collections.emptyList()
    );

    @Before
    public void setUp() throws Exception {
        when(toDoRepository.findAllByUserId(userId)).thenReturn(ImmutableList.of(todoFixture));
    }

    @Test
    public void shouldReturn401ForUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(unauthenticated());
    }

    @Test
    public void shouldReturnItemsListWithAuthentication() throws Exception {
        mockMvc.perform(
                get("/todos")
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(todoId))
                .andExpect(jsonPath("$[0].text").value("foo"));
    }

//    @Test
//    public void shouldReturnItemsListWithAuthorizationHeader() throws Exception {
//        String token = jwtSignature.generateToken(new HashMap<String, Object>() {{
//            put("userId", userId);
//        }});
//        mockMvc.perform(
//                get("/todos")
//                        .header(HttpHeaders.AUTHORIZATION, Constants.BEARER_TOKEN_PREFIX + token)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(todoId))
//                .andExpect(jsonPath("$[0].text").value("foo"));
//    }

    @Test
    public void shouldReturnItemsListWithManuallySetSecurityContext() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        mockMvc.perform(get("/todos"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(todoId))
//                .andExpect(jsonPath("$[0].text").value("foo"));

        List<ToDo> list = toDoService.list();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getId(), is(todoId));
        assertThat(list.get(0).getText(), is("foo"));
    }

    @Ignore
    @Test
    @WithMockUser(username = "wukun", password = "123", authorities = {"admin", "dev"})
    public void shouldReturnItemsListWithMockUser() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(todoId))
                .andExpect(jsonPath("$[0].text").value("foo"));
    }
}