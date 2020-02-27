package com.demo.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.todo.data.Todo
import com.demo.todo.data.TodoRepository

class ListViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val allTodos: LiveData<List<Todo>> = todoRepository.getAllTodos()
    val upcomingTodosCount: LiveData<Int> = todoRepository.getUpcomingTodosCount()

    fun toggleTodo(id: String) {
        todoRepository.toggleTodo(id)
    }

}