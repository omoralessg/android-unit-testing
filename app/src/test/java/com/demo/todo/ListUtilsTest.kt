package com.demo.todo

import com.demo.todo.list.determineCardColor
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ListUtilsTest(
    private val expected: Int,
    private val dueDate: Long?,
    private val done: Boolean,
    private val scenario: String
) {
    companion object{
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}")
        fun todos() = listOf(
            arrayOf(R.color.todoDone, null, true, "Done, no date"),
            arrayOf(R.color.todoNotDue, null, false, "Not Done, no date"),
            arrayOf(R.color.todoOverDue, now - day, false, "Not Done, due yesterday")
        )

    }


    @Test
    fun test_determineCardColor(){
        val actual = determineCardColor(dueDate, done)
        assertEquals(expected, actual)
    }

/*    @Test
    fun test_determineCardColorNotDone(){
        val expected = R.color.todoNotDue
        val dueDate = null
        val done = false
        val actual = determineCardColor(dueDate, done)
        assertEquals(expected, actual)
    }

    @Test
    fun test_determineCardColorOverDue(){
        val expected = R.color.todoOverDue
        val dueDate = now - day
        val done = false
        val actual = determineCardColor(dueDate, done)
        assertEquals(expected, actual)
    }*/
}