package com.hoc081098.solivagant.sample.todo.features.detail

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.navigation.requireRoute

internal class DetailViewModel(
  private val navigator: NavEventNavigator,
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val route = savedStateHandle.requireRoute<DetailScreenRoute>()

  internal fun navigateBack() = navigator.navigateBack()
}
