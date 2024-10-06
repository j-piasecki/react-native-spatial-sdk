package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReactContext
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.core.Vector3
import com.meta.spatial.toolkit.Scale

class ScaleView(reactContext: ReactContext) :
  ReactViewGroup(reactContext),
  ComponentHolder {
  override var component = Scale(1f)
    private set

  fun setScale(x: Float, y: Float, z: Float) = tryReattachAfter {
    component.scale = Vector3(x, y, z)
  }

  override fun onAttachedToWindow() = tryReattachAfter {
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    tryReattachAfter {
      component = Scale(1f)
    }

    super.onDetachedFromWindow()
  }
}
