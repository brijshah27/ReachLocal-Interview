package com.reachlocal.todo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/todos", produces = { MediaType.APPLICATION_JSON_VALUE })
@RestController
public class TodoCtrl {
	private TodoRepo todoRepo;

	public TodoCtrl(TodoRepo todoRepo) {
		this.todoRepo = todoRepo;
	}

	@GetMapping
	public Iterable<Todo> getAll() {
		Iterable<Todo> todos = todoRepo.findAll();
		return todos;
	}
	
	/*
	 This method handles request of adding one single Todo.
	 
	 Steps to handle the request:
	 1. Check for length of todo, if it's is more than 200 return with error.
	 2. Get all the todos from the database and compare current todo description against the description of the todos from the databse, if found match 
	 return with error.
	 3. If all goes well, add todos to database.
	 */

	@PostMapping("/addOneTodo")
	public void addOneTodo(Todo todo) {

		if (todo.description.length() > 200) {
			System.out.println("Error, length of todo is greater than 200");
			return;
		}

		List<Todo> presentTodos = (List<Todo>) todoRepo.findAll();

		if (presentTodos.stream().anyMatch(t -> t.description.toUpperCase() == todo.description.toUpperCase())) {
			System.out.println("Error, try adding duplicate todo");
			return;
		}

		else {
			todoRepo.save(todo);
		}
	}
	
	
	/*Helper method that creates predicate on description for distinct todos*/
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
	{
	    Map<Object, Boolean> map = new ConcurrentHashMap<>();
	    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	
	/*Helper method to filter todos based on length of description */
	public static boolean descriptionFilter(String description) {
		if(description.length()>200) {
			System.out.println("Error, Todo length is more than 200");
			return false;
		}
		else return true;
	}
	
	
	/*
	 This method handles request of adding list of Todos.
	 
	 *Here, comparing the list of todos with all the todos in database can be expensive to we're just checking incoming list of todos for duplicates.*
	 
	 Steps to handle the request:
	 1. Check if incoming list of todo is null or contains empty list then return with error.
	 2. Filter the all the todos in the list with description length of 200, whichever todo has length more than 200 gets filter out.
	 3. Remove duplicate todos based on description.
	 4. Finally, limit adding 10 todos per request.
	 5. Add todos to the database.
	 */
	@PostMapping("/addTodoList")
	public void addTodos(List<Todo> todos) {
		if(todos==null || todos.size()==0) {
			System.out.println("Error, adding empty todos");
		}
		
		todos = todos.stream()
				.filter(todo -> todo.description.length() < 200)
				.filter(distinctByKey(p -> descriptionFilter(p.description)==true))
				.limit(10)
				.collect(Collectors.toList());
		if(todos == null || todos.size()==0) {
			System.out.println("Error, none of the todos meet criteria");
		}
		else
			todos.stream().map(todo -> todoRepo.save(todo));		
	}
	
	/*
	 This methdo handles request of deleting todo from the database
	 Steps to handle request:
	 1. Check if todo is null, return with error.
	 2. If all goes well delete the todo from the database.
	 */
	@PostMapping("/deleteTodo")
	public void deleteTodo(Todo todo) {
		if(todo==null)
			System.out.println("Error, deleting the todo");
		
		else
		todoRepo.delete(todo);
	}
}
