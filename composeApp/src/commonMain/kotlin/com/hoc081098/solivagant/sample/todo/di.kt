package com.hoc081098.solivagant.sample.todo

import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.sample.todo.data.DataModule
import com.hoc081098.solivagant.sample.todo.features.detail.DetailModule
import com.hoc081098.solivagant.sample.todo.features.edit.EditModule
import com.hoc081098.solivagant.sample.todo.features.home.HomeModule
import com.hoc081098.solivagant.sample.todo.features.utils.SingleEventChannel
import org.koin.core.context.startKoin
import org.koin.core.module.KoinApplicationDslMarker
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val CommonModule = module {
  includes(DataModule)

  singleOf(::NavEventNavigator)
  factory { SingleEventChannel<Any?>() }

  includes(HomeModule)
  includes(DetailModule)
  includes(EditModule)
}

@KoinApplicationDslMarker
fun startKoinCommon(appDeclaration: KoinAppDeclaration = {}) {
  startKoin {
    appDeclaration()
    modules(CommonModule)
  }
}
