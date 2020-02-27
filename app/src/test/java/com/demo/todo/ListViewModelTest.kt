@file:Suppress("IncorrectScope")

package com.demo.todo

import androidx.lifecycle.MutableLiveData
import com.demo.todo.data.Todo
import com.demo.todo.data.TodoRepository
import com.demo.todo.list.ListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalArgumentException

class ListViewModelTest {
    @get:Rule
    val exceptionRule: ExpectedException = ExpectedException.none()
    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    @Test
    fun test_allTodosEmpty() {
        val expected = 0
        val repository: TodoTestRepository = mock(verboseLogging = true)
        whenever(repository.getAllTodos())
                .thenReturn(MutableLiveData(arrayListOf()))

        val model = ListViewModel(repository)
        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_allTodosSingle() {
        val expected = 1
        val repository: TodoTestRepository = mock()
        whenever(repository.getAllTodos())
                .thenReturn(MutableLiveData(arrayListOf(
                        Todo("5", "Todo 5", now - day, false, now)
                )))

        val model = ListViewModel(repository)
        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_allTodosMultiple() {
        val expected = 3
        val repository: TodoTestRepository = mock()
        whenever(repository.getAllTodos())
                .thenReturn(MutableLiveData(arrayListOf(
                        Todo("5", "Todo 5", now - day, false, now),
                        Todo("4", "Todo 4", now + day, true, now),
                        Todo("3", "Todo 3", now + day, false, now)
                )))

        val model = ListViewModel(repository)
        val todos = model.allTodos.value
        assertNotNull(todos)
        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_upcomingTodosCountEmpty() {
        val repository: TodoRepository = mock()
        val expected = 0
        whenever(repository.getUpcomingTodosCount())
                .thenReturn(MutableLiveData(expected))

        val model = ListViewModel(repository)
        val todoCount = model.upcomingTodosCount.value
        assertNotNull(todoCount)
        assertEquals(expected, todoCount)
    }

    @Test
    fun test_upcomingTodosCountSingle() {
        val repository: TodoRepository = mock()
        val expected = 1
        whenever(repository.getUpcomingTodosCount())
                .thenReturn(MutableLiveData(expected))

        val model = ListViewModel(repository)
        val todoCount = model.upcomingTodosCount.value
        assertNotNull(todoCount)
        assertEquals(expected, todoCount)
    }

    @Test
    fun test_upcomingTodosCountMultiple() {
        val repository: TodoRepository = mock()
        val expected = 5
        whenever(repository.getUpcomingTodosCount())
                .thenReturn(MutableLiveData(expected))

        val model = ListViewModel(repository)
        val todoCount = model.upcomingTodosCount.value
        assertNotNull(todoCount)
        assertEquals(expected, todoCount)
    }

    @Test
    fun test_toggleTodo() {
        val repository: TodoRepository = mock()
        val id = "fake"
        val model = ListViewModel(repository)
        model.toggleTodo(id)
        verify(repository)
                .toggleTodo(id)
    }

    @Test
    fun test_toggleTodoNotFound() {
        val repository: TodoRepository = mock()
        val exceptionMessage = "Todo not found"
        val id = "fake"
        whenever(repository.toggleTodo(id))
                .thenThrow(IllegalArgumentException(exceptionMessage))
        val model = ListViewModel(repository)
        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage(exceptionMessage)
        model.toggleTodo(id)
        verify(repository)
                .toggleTodo(id)
    }
}