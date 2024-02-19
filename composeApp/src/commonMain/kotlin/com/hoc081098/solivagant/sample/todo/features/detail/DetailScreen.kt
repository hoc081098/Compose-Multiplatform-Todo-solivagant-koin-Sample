package com.hoc081098.solivagant.sample.todo.features.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hoc081098.kmp.viewmodel.koin.compose.koinKmpViewModel
import com.hoc081098.solivagant.sample.todo.features.MARGIN_SCROLLBAR
import com.hoc081098.solivagant.sample.todo.features.home.HomeUiState.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
  modifier: Modifier = Modifier,
  viewModel: DetailViewModel = koinKmpViewModel(),
) {
  Scaffold(
    modifier = modifier,
    topBar = {
      CenterAlignedTopAppBar(
        title = { Text("Detail") },
        navigationIcon = {
          IconButton(onClick = viewModel::navigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Default.ArrowBack,
              contentDescription = null,
            )
          }
        },
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = {},
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
        )
      }
    },
  ) { innerPadding ->
    Box(
      modifier = Modifier.fillMaxSize()
        .padding(innerPadding)
        .consumeWindowInsets(innerPadding),
      contentAlignment = Alignment.Center,
    ) {

    }
  }
}

@Composable
private fun Item(
  item: TodoItem,
  onClicked: (TodoItem) -> Unit,
  onToggle: (TodoItem) -> Unit,
  onRemove: (TodoItem) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.clickable(onClick = { onClicked(item) }),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Spacer(modifier = Modifier.width(8.dp))

    Checkbox(
      checked = item.isDone,
      onCheckedChange = { onToggle(item) },
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
      text = AnnotatedString(item.text),
      modifier = Modifier.weight(1f),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )

    Spacer(modifier = Modifier.width(8.dp))

    IconButton(onClick = { onRemove(item) }) {
      Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = null,
      )
    }

    Spacer(modifier = Modifier.width(MARGIN_SCROLLBAR))
  }
}
