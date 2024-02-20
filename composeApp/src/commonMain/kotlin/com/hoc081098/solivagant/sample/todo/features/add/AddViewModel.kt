package com.hoc081098.solivagant.sample.todo.features.add

import androidx.compose.runtime.Immutable
import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.sample.todo.domain.TodoItem
import com.hoc081098.solivagant.sample.todo.features.add.domain.AddTodoItem
import com.hoc081098.solivagant.sample.todo.features.utils.HasSingleEventFlow
import com.hoc081098.solivagant.sample.todo.features.utils.SingleEventChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
internal sealed interface AddUiState {
  data class Content(
    val text: String,
    val isDone: Boolean,
    val isIdle: Boolean,
  ) : AddUiState

  data object Complete : AddUiState
}

@Immutable
internal sealed interface AddSingleEvent {
  data object ConfirmBack : AddSingleEvent
}

internal class AddViewModel(
  private val addTodoItem: AddTodoItem,
  private val navigator: NavEventNavigator,
  private val singleEventChannel: SingleEventChannel<AddSingleEvent>,
) : ViewModel(),
  HasSingleEventFlow<AddSingleEvent> by singleEventChannel {
  private val _uiStateFlow = MutableStateFlow<AddUiState>(
    AddUiState.Content(
      text = "",
      isDone = false,
      isIdle = true,
    ),
  )

  internal val uiStateFlow: StateFlow<AddUiState> = _uiStateFlow.asStateFlow()
  internal val backPressesFlow: Flow<Unit> = navigator.backPresses()

  internal fun onTextChange(text: String) {
    _uiStateFlow.update {
      if (it is AddUiState.Content) {
        it.copy(text = text, isIdle = false)
      } else {
        AddUiState.Content(text = text, isDone = false, isIdle = false)
      }
    }
  }

  internal fun onDoneChange(isDone: Boolean) {
    _uiStateFlow.update {
      if (it is AddUiState.Content) {
        it.copy(isDone = isDone, isIdle = false)
      } else {
        AddUiState.Content(text = "", isDone = isDone, isIdle = false)
      }
    }
  }

  internal fun save() {
    viewModelScope.launch {
      val content = _uiStateFlow.value as? AddUiState.Content
        ?: return@launch

      TodoItem.Text.of(content.text)
        .mapCatching { text ->
          addTodoItem(
            text = text,
            isDone = content.isDone,
          ).getOrThrow()
        }
        .fold(
          onSuccess = {
            _uiStateFlow.value = AddUiState.Complete
            navigator.navigateBack()
          },
          onFailure = {
            // TODO: handle error
          },
        )
    }
  }

  internal fun navigateBack() = navigator.navigateBack()

  internal fun confirmBack() {
    when (val s = _uiStateFlow.value) {
      is AddUiState.Content ->
        if (s.isIdle) {
          navigator.navigateBack()
        } else {
          viewModelScope.launch { singleEventChannel.sendEvent(AddSingleEvent.ConfirmBack) }
        }

      else ->
        navigator.navigateBack()
    }
  }
}
