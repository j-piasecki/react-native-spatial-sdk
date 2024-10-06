package com.jpiasecki.spatialsdk

import android.graphics.Color
import com.facebook.react.bridge.ReactContext
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.toolkit.Color4
import com.meta.spatial.toolkit.Material

class MaterialView(reactContext: ReactContext) :
  ReactViewGroup(reactContext),
  ComponentHolder {
  override var component = Material()
    private set

  fun setColor(color: Int) = tryReattachAfter {
    component.baseColor = Color4(
      Color.red(color) / 255f,
      Color.green(color) / 255f,
      Color.blue(color) / 255f,
      Color.alpha(color) / 255f,
    )
  }

  override fun onAttachedToWindow() = tryReattachAfter {
    super.onAttachedToWindow()
  }

  override fun onDetachedFromWindow() {
    tryReattachAfter {
      component = Material()
    }

    super.onDetachedFromWindow()
  }
}
