package com.hoc081098.solivagant.sample.todo.features.utils

internal expect class WeakReference<T : Any> constructor(reference: T) {
  fun get(): T?
}
