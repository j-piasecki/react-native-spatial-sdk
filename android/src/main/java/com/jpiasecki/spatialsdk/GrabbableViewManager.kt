package com.jpiasecki.spatialsdk

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialGrabbableViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialGrabbableViewManagerInterface
import com.meta.spatial.toolkit.GrabbableType

@ReactModule(name = GrabbableViewManager.NAME)
class GrabbableViewManager :
  ViewGroupManager<GrabbableView>(),
  RNSpatialGrabbableViewManagerInterface<GrabbableView> {
  private val delegate = RNSpatialGrabbableViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): GrabbableView = GrabbableView(reactContext)

  override fun setEnabled(view: GrabbableView, value: Boolean) {
    view.setIsEnabled(value)
  }

  override fun setType(view: GrabbableView, value: String?) {
    val type = if (value == "pivot_y") GrabbableType.PIVOT_Y else GrabbableType.FACE
    view.setType(type)
  }

  companion object {
    const val NAME = "RNSpatialGrabbableView"
  }
}
