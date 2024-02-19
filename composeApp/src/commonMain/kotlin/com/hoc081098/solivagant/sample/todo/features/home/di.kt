package com.hoc081098.solivagant.sample.todo.features.home

import com.hoc081098.solivagant.sample.todo.features.home.domain.ObserveAllTodoItems
import com.hoc081098.solivagant.sample.todo.features.home.domain.RemoveItemById
import com.hoc081098.solivagant.sample.todo.features.home.domain.ToggleItemById
import kotlin.jvm.JvmField
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@JvmField
internal val HomeModule = module {
  factoryOf(::HomeViewModel)
  factoryOf(::ObserveAllTodoItems)
  factoryOf(::RemoveItemById)
  factoryOf(::ToggleItemById)
}
