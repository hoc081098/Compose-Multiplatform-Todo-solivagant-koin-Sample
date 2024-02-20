package com.hoc081098.solivagant.sample.todo.features.detail

import com.hoc081098.solivagant.sample.todo.features.detail.domain.ObserveTodoItemById
import kotlin.jvm.JvmField
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

@JvmField
internal val DetailModule = module {
  factoryOf(::DetailViewModel)
  factoryOf(::ObserveTodoItemById)
}
