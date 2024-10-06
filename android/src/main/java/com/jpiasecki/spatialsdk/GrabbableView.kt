package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.core.Entity
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.GrabbableType

class GrabbableView(reactContext: ReactContext) :
  ReactViewGroup(reactContext),
  ComponentHolder {
  override val component = Grabbable()

  fun setIsEnabled(enabled: Boolean) = tryReattachAfter {
    component.enabled = enabled
  }

  fun setType(type: GrabbableType) = tryReattachAfter {
    component.type = type
  }

  override fun onAttachedToWindow() = tryReattachAfter {
    super.onAttachedToWindow()

    val entity = findEntity(this)?.entity ?: return@tryReattachAfter
    entity.setComponent(GrabbableObserverComponent(UIManagerHelper.getSurfaceId(this), this.id))
  }

  override fun attachToEntity(entity: Entity) {
    super.attachToEntity(entity)

    entity.setComponent(GrabbableObserverComponent(UIManagerHelper.getSurfaceId(this), this.id))
  }

  override fun onDetachedFromWindow() {
    tryReattachAfter {
      component.enabled = false

      val entity = findEntity(this)?.entity ?: return@tryReattachAfter
      if (entity.hasComponent<GrabbableObserverComponent>()) {
        val observer = entity.getComponent<GrabbableObserverComponent>()
        observer.enabled = false
        entity.setComponent(observer)
      }
    }

    super.onDetachedFromWindow()
  }
}
