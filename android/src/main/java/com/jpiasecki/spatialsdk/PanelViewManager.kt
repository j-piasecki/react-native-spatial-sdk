package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialPanelViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialPanelViewManagerInterface

@ReactModule(name = PanelViewManager.NAME)
class PanelViewManager :
  ViewGroupManager<PanelView>(),
  RNSpatialPanelViewManagerInterface<PanelView> {
  private val delegate = RNSpatialPanelViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): PanelView = PanelView(reactContext)

  override fun setPanelId(view: PanelView, id: Int) {
    view.setPanelId(id)
  }

  override fun setPosition(view: PanelView, value: ReadableArray?) {
    if (value != null) {
      view.setPosition(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  override fun setOrientation(view: PanelView, value: ReadableArray?) {
    if (value != null) {
      view.setOrientation(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  companion object {
    const val NAME = "RNSpatialPanelView"
  }
}
