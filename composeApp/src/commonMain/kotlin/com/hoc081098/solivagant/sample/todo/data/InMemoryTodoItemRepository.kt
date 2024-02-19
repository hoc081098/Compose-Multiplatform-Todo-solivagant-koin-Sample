package com.hoc081098.solivagant.sample.todo.data

import com.hoc081098.flowext.ignoreElements
import com.hoc081098.flowext.plus
import com.hoc081098.flowext.select
import com.hoc081098.flowext.timer
import com.hoc081098.solivagant.sample.todo.domain.TodoItem
import com.hoc081098.solivagant.sample.todo.domain.TodoItemRepository
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized

@OptIn(InternalCoroutinesApi::class)
internal class InMemoryTodoItemRepository : TodoItemRepository {
  private val idLock = SynchronizedObject()
  private var currentId = 0L
  private fun generateId() = synchronized(idLock) { currentId++ }
    .toString()
    .let(TodoItem::Id)

  private suspend inline fun fakeTimerDelay() = delay(200.milliseconds)
  private val fakeTimerFlow = timer(Unit, 2.seconds).ignoreElements()

  private val itemsStateFlow = MutableStateFlow(
    listOf(
      TodoItem(
        id = generateId(),
        text = TodoItem.Text
          .of(value = "solivagant is Compose Multiplatform Navigation library")
          .getOrThrow(),
        isDone = true,
      ),
      TodoItem(
        id = generateId(),
        text = TodoItem.Text
          .of(value = "solivagant: Pragmatic, type safety navigation for Compose Multiplatform")
          .getOrThrow(),
        isDone = true,
      ),
      TodoItem(
        id = generateId(),
        text = TodoItem.Text
          .of(value = "solivagant: ViewModel, SavedStateHandle, Lifecycle, Multi-Backstacks, Transitions, Back-press handling, and more...")
          .getOrThrow(),
        isDone = true,
      ),
    ),
  )

  override fun observeAll() = fakeTimerFlow + itemsStateFlow

  override fun observeById(id: TodoItem.Id) =
    fakeTimerFlow +
        itemsStateFlow.select { items -> items.find { it.id == id } }

  override suspend fun add(text: TodoItem.Text, isDone: Boolean): Result<Unit> {
    fakeTimerDelay()
    itemsStateFlow.update {
      it + TodoItem(
        id = generateId(),
        text = text,
        isDone = isDone,
      )
    }

    return Result.success(Unit)
  }

  override suspend fun removeById(id: TodoItem.Id): Result<Unit> {
    fakeTimerDelay()
    itemsStateFlow.update { items -> items.filterNot { it.id == id } }

    return Result.success(Unit)
  }

  override suspend fun toggleById(id: TodoItem.Id): Result<TodoItem> {
    fakeTimerDelay()
    val updated = itemsStateFlow.updateAndGet { items ->
      items.map {
        if (it.id == id) it.copy(isDone = !it.isDone)
        else it
      }
    }

    return runCatching { updated.first { it.id == id } }
  }

  override suspend fun update(item: TodoItem): Result<TodoItem> {
    fakeTimerDelay()
    itemsStateFlow.update { items ->
      items.map {
        if (it.id == item.id) item
        else it
      }
    }

    return Result.success(item)
  }
}
