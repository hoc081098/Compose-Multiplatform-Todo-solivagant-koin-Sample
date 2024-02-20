package com.hoc081098.solivagant.sample.todo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AndroidTodoApp : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoinCommon {
      androidContext(this@AndroidTodoApp)
      androidLogger(
        level = if (BuildConfig.DEBUG) {
          Level.DEBUG
        } else {
          Level.NONE
        },
      )
    }
  }
}
