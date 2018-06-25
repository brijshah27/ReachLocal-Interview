package com.reachlocal.todo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@DataJpaTest
class TodoRepoTest extends Specification {
    @Autowired
    TodoRepo todoRepo

    def "Should support reading existing Todos"(){
        expect: "the todos are read"
        todoRepo.findAll().each { Todo todo ->
            with(todo){
                // see import.sql for initial list of Todos
                description =~ /my TODO description \d/
            }
        }
    }

    def "Should support persisting a Todo"(){
        given: "a todo to be persisted"
        Todo todo = new Todo(description: "my description")

        when: "the todo is persisted"
        todo = todoRepo.save(todo)

        then: "an ID is returned"
        todo.id > 0
    }
}
