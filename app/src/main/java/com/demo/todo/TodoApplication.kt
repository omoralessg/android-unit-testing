package com.demo.todo

import android.app.Application
import com.demo.todo.data.TodoRepository
import com.demo.todo.data.TodoRoomDatabase
import com.demo.todo.data.TodoRoomRepository

class TodoApplication : Application() {

    val todoRepository: TodoRepository
        get() = TodoRoomRepository(TodoRoomDatabase.getInstance(this.applicationContext)!!.todoDao())
}