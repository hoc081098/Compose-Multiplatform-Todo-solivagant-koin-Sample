package com.hoc081098.solivagant.sample.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.hoc081098.solivagant.navigation.NavDestination
import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.navigation.NavHost
import com.hoc081098.solivagant.sample.todo.features.detail.DetailScreenDestination
import com.hoc081098.solivagant.sample.todo.features.home.HomeScreenDestination
import com.hoc081098.solivagant.sample.todo.features.home.HomeScreenRoute
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Stable
private val AllDestinations: ImmutableSet<NavDestination> = persistentSetOf(
  HomeScreenDestination,
  DetailScreenDestination,
)

@Composable
fun App(
  modifier: Modifier = Modifier,
  navigator: NavEventNavigator = koinInject(),
) {
  KoinContext {
    MaterialTheme {
      Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
      ) {
        NavHost(
          modifier = Modifier.fillMaxSize(),
          startRoute = HomeScreenRoute,
          destinations = AllDestinations,
          navEventNavigator = navigator,
          destinationChangedCallback = { route ->
            println("Destination changed: $route")
          },
        )
      }
    }
  }
}
