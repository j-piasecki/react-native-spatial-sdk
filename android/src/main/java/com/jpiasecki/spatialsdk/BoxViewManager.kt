package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialBoxViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialBoxViewManagerInterface

@ReactModule(name = PanelViewManager.NAME)
class BoxViewManager :
  ViewGroupManager<BoxView>(),
  RNSpatialBoxViewManagerInterface<BoxView> {
  private val delegate = RNSpatialBoxViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): BoxView = BoxView(reactContext)

  override fun setPosition(view: BoxView, value: ReadableArray?) {
    if (value != null) {
      view.setPosition(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  override fun setOrientation(view: BoxView, value: ReadableArray?) {
    if (value != null) {
      view.setOrientation(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  override fun setWidth(view: BoxView, value: Double) {
    view.setWidth(value.toFloat())
  }

  override fun setHeight(view: BoxView, value: Double) {
    view.setHeight(value.toFloat())
  }

  override fun setDepth(view: BoxView, value: Double) {
    view.setDepth(value.toFloat())
  }

  override fun setPositionRelativeToParent(view: BoxView, value: Boolean) {
    view.setPositionRelativeToParent(value)
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> =
    mutableMapOf(
      PositionChangeEvent.NAME to mapOf("registrationName" to "onPositionChange"),
      OrientationChangeEvent.NAME to mapOf("registrationName" to "onOrientationChange"),
    )

  companion object {
    const val NAME = "RNSpatialBoxView"
  }
}
