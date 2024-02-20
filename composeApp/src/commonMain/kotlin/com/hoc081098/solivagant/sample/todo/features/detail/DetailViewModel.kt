package com.hoc081098.solivagant.sample.todo.features.detail

import androidx.compose.runtime.Immutable
import com.hoc081098.flowext.startWith
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.navigation.requireRoute
import com.hoc081098.solivagant.sample.todo.domain.TodoItem
import com.hoc081098.solivagant.sample.todo.features.detail.DetailUiState.TodoItemUi
import com.hoc081098.solivagant.sample.todo.features.detail.domain.ObserveTodoItemById
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Immutable
internal sealed interface DetailUiState {
  data object Loading : DetailUiState
  data class Error(val message: String) : DetailUiState
  data class Content(val item: TodoItemUi?) : DetailUiState

  @Immutable
  data class TodoItemUi(
    val id: String,
    val text: String,
    val isDone: Boolean,
  )
}

internal class DetailViewModel(
  private val navigator: NavEventNavigator,
  observeTodoItemById: ObserveTodoItemById,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val route = savedStateHandle.requireRoute<DetailScreenRoute>()

  internal val uiStateFlow: StateFlow<DetailUiState> =
    observeTodoItemById(TodoItem.Id(route.id))
      .map { item ->
        DetailUiState.Content(
          item = item?.toTodoItemUi(),
        )
      }
      .startWith(DetailUiState.Loading)
      .catch {
        emit(
          DetailUiState.Error(
            message = it.message ?: "Unknown error",
          ),
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailUiState.Loading,
      )

  internal fun navigateBack() = navigator.navigateBack()
}

private fun TodoItem.toTodoItemUi(): TodoItemUi = TodoItemUi(
  id = id.value,
  text = text.value,
  isDone = isDone,
)
