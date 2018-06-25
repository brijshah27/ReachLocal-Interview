package com.reachlocal.todo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [TodoApplication])
@WebMvcTest(TodoCtrl)
class TodoCtrlTest extends Specification {
    @Autowired
    MockMvc mockMvc

    @Autowired
    WebApplicationContext webApplicationContext

    @Autowired
    TodoRepo mockTodoRepo

    void setup() {

    }

    void cleanup() {

    }

    def "Supports reading existing Todos"() {
        given: "an existing list of todos"
        List<Todo> todos = todos()

        when: "a read request is made for all todos"
        ResultActions resultActions = mockMvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON))

        then: "a success status is returned"
        resultActions.andExpect(status().is(HttpStatus.OK.value()))

        and: "the list of existing todos is returned"
        1 * mockTodoRepo.findAll() >> todos
        resultActions.andExpect(jsonPath('$').isArray())
        todos.eachWithIndex { todo, i ->
            resultActions.andExpect(jsonPath("\$[${i}].description").value(todo.description))
        }
    }

    List<Todo> todos() {
        [
            new Todo("my desc1"),
            new Todo("my desc2"),
            new Todo("my desc3"),
            new Todo("my desc4"),
        ]
    }

    @TestConfiguration
    static class MockConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        TodoRepo todoRepoMock() {
            detachedMockFactory.Mock(TodoRepo)
        }
    }
}
