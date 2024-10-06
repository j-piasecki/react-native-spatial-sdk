package com.jpiasecki.spatialsdk

import android.view.ViewGroup
import androidx.core.view.children
import com.meta.spatial.core.ComponentBase
import com.meta.spatial.core.Entity

interface ComponentHolder {
  val component: ComponentBase

  fun findEntity(view: ViewGroup): EntityHolder? {
    var current = view

    while (current !is EntityHolder) {
      current = current.children.firstOrNull() as? ViewGroup ?: return null
    }

    return current
  }

  fun tryAttachToEntity(view: ViewGroup) {
    val entity = findEntity(view)?.entity ?: return
    entity.setComponent(component)
  }

  fun attachToEntity(entity: Entity) {
    entity.setComponent(component)
  }

  fun tryReattachAfter(fn: () -> Unit) {
    assert(this is ViewGroup) { "Expected ComponentHolder to be a ViewGroup" }
    fn()
    tryAttachToEntity(this as ViewGroup)
  }
}
