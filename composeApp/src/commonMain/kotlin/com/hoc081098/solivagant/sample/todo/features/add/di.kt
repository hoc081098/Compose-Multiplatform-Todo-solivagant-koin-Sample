package com.hoc081098.solivagant.sample.todo.features.add

import com.hoc081098.solivagant.sample.todo.features.add.domain.AddTodoItem
import kotlin.jvm.JvmField
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@JvmField
internal val AddModule = module {
  factoryOf(::AddViewModel)
  factoryOf(::AddTodoItem)
}
