package com.hoc081098.solivagant.sample.todo.features.home

import androidx.compose.runtime.Immutable
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Immutable
internal sealed interface HomeUiState {
  data object Loading : HomeUiState
  data class Error(val message: String) : HomeUiState
  data class Data(val items: ImmutableList<TodoItem>) : HomeUiState

  @Immutable
  data class TodoItem(
    val id: String,
    val text: String,
    val isDone: Boolean,
  )
}

internal class HomeViewModel : ViewModel() {
  internal val uiStateFlow: MutableStateFlow<HomeUiState> = MutableStateFlow(
    HomeUiState.Data(
      items = List(100) {
        HomeUiState.TodoItem(
          id = it.toString(),
          text = "Todo item $it",
          isDone = it % 2 == 0,
        )
      }.toPersistentList(),
    ),
  )

  init {
    viewModelScope.launch {
      uiStateFlow.value = HomeUiState.Error("Some error occurred!")
    }
  }
}
