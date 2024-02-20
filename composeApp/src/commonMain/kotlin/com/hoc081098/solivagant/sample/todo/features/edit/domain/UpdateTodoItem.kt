package com.hoc081098.solivagant.sample.todo.features.edit.domain

import com.hoc081098.solivagant.sample.todo.domain.TodoItem
import com.hoc081098.solivagant.sample.todo.domain.TodoItemRepository

internal class UpdateTodoItem(
  private val todoItemRepository: TodoItemRepository,
) {
  suspend operator fun invoke(item: TodoItem): Result<TodoItem> =
    todoItemRepository.update(item)
}
