package com.hoc081098.solivagant.sample.todo.features.home.domain

import com.hoc081098.solivagant.sample.todo.domain.TodoItem
import com.hoc081098.solivagant.sample.todo.domain.TodoItemRepository
import kotlinx.coroutines.flow.Flow

internal class ObserveAllTodoItems(
  private val todoItemRepository: TodoItemRepository,
) {
  operator fun invoke(): Flow<List<TodoItem>> = todoItemRepository.observeAll()
}
