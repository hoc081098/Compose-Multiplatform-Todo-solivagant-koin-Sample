package com.hoc081098.solivagant.sample.todo.data

import com.hoc081098.solivagant.sample.todo.domain.TodoItemRepository
import kotlin.jvm.JvmField
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@JvmField
internal val DataModule = module {
  singleOf(::InMemoryTodoItemRepository) { bind<TodoItemRepository>() }
}
