package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialScaleViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialScaleViewManagerInterface

@ReactModule(name = GrabbableViewManager.NAME)
class ScaleViewManager :
  ViewGroupManager<ScaleView>(),
  RNSpatialScaleViewManagerInterface<ScaleView> {
  private val delegate = RNSpatialScaleViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): ScaleView = ScaleView(reactContext)

  override fun setScale(view: ScaleView, value: ReadableArray?) {
    if (value != null) {
      if (value.size() == 1) {
        val scale = value.getDouble(0).toFloat()
        view.setScale(scale, scale, scale)
      } else if (value.size() == 3) {
        view.setScale(
          value.getDouble(0).toFloat(),
          value.getDouble(1).toFloat(),
          value.getDouble(2).toFloat(),
        )
      }
    }
  }

  companion object {
    const val NAME = "RNSpatialScaleView"
  }
}
