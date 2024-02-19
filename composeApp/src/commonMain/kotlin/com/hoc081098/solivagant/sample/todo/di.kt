package com.hoc081098.solivagant.sample.todo

import com.hoc081098.solivagant.navigation.NavEventNavigator
import com.hoc081098.solivagant.sample.todo.features.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val CommonModule = module {
  singleOf(::NavEventNavigator)
  factoryOf(::HomeViewModel)
}

fun startKoinCommon(appDeclaration: KoinAppDeclaration = {}) {
  startKoin {
    appDeclaration()
    modules(CommonModule)
  }
}
