@file:Suppress("IncorrectScope")

package com.demo.todo

import com.demo.todo.add.AddViewModel
import com.demo.todo.data.TodoRepository
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class AddViewModelTest {
    @get:Rule
    val rule = MockitoJUnit.collector()

    @Test
    fun test_save() {
        val repository: TodoRepository = mock()
        val model = AddViewModel(repository)
        model.todo.title = "Test Todo"
        val actual = model.save()
        assertNull(actual)
        verify(repository).insert(any())
    }

    @Test
    fun test_saveNoTitle() {
        val repository: TodoRepository = mock()
        val model = AddViewModel(repository)
        val expected = "Title is required"
        val actual = model.save()
        assertEquals(expected, actual)
        verify(repository, never()).insert(any())
    }

    @Test
    fun test_saveWithDate() {
        val repository: TodoRepository = mock()
        val model = AddViewModel(repository)
        val actualTitle = "Test Todo"
        model.todo.title = actualTitle
        val actualDate = System.currentTimeMillis()
        model.todo.dueDate = actualDate
        val actual = model.save()
        assertNull(actual)
        verify(repository).insert(
                argThat {
                    title == actualTitle && dueDate == actualDate
                }
        )
    }

    @Test
    fun test_saveWithoutDate() {
        val repository: TodoRepository = mock()
        val model = AddViewModel(repository)
        val actualTitle = "Test Todo"
        model.todo.title = actualTitle
        val actual = model.save()
        assertNull(actual)
        verify(repository).insert(any())
//        verify(repository.insert(
//                argThat { created == System.currentTimeMillis() }
//        ))
        verify(repository).insert(
                argThat {
                    title == actualTitle && dueDate == null
                }
        )
    }
}