package com.jpiasecki.spatialsdk

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.viewmanagers.RNSpatialPanelViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialPanelViewManagerInterface

@ReactModule(name = PanelViewManager.NAME)
class PanelViewManager : SimpleViewManager<PanelView>(), RNSpatialPanelViewManagerInterface<PanelView> {
  private val delegate = RNSpatialPanelViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): PanelView {
    return PanelView(reactContext)
  }

  override fun setPanelId(view: PanelView, id: Int) {
    view.setPanelId(id)
  }

  companion object {
    const val NAME = "RNSpatialPanelView"
  }
}

