package com.hoc081098.solivagant.sample.todo.features.edit

import com.hoc081098.solivagant.sample.todo.features.edit.domain.ObserveTodoItemById
import com.hoc081098.solivagant.sample.todo.features.edit.domain.UpdateTodoItem
import kotlin.jvm.JvmField
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@JvmField
internal val EditModule = module {
  factoryOf(::EditViewModel)
  factoryOf(::UpdateTodoItem)
  factoryOf(::ObserveTodoItemById)
}
