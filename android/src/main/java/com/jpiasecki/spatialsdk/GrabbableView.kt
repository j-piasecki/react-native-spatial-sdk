package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReactContext
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.GrabbableType

class GrabbableView(reactContext: ReactContext): ReactViewGroup(reactContext), ComponentHolder {
  override val component = Grabbable()

  fun setIsEnabled(enabled: Boolean) = tryReattachAfter {
    component.enabled = enabled
  }

  fun setType(type: GrabbableType) = tryReattachAfter {
    component.type = type
  }

  override fun onAttachedToWindow() = tryReattachAfter {
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    tryReattachAfter {
      component.enabled = false
    }

    super.onDetachedFromWindow()
  }
}
