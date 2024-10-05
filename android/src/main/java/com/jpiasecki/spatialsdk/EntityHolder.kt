package com.jpiasecki.spatialsdk

import android.view.ViewGroup
import com.facebook.react.ReactRootView
import com.meta.spatial.core.Entity

interface EntityHolder {
  val entity: Entity?

  fun tryAttachComponents(view: ViewGroup) {
    assert(entity != null) { "Trying to attach components to an uninitialized entity" }

    var current = view.parent as? ViewGroup

    while (current != null && current !is EntityHolder && current !is ReactRootView) {
      if (current is ComponentHolder) {
        current.attachToEntity(entity!!)
      }

      current = current.parent as? ViewGroup
    }
  }
}
