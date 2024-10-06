package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialMeshViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialMeshViewManagerInterface

@ReactModule(name = PanelViewManager.NAME)
class MeshViewManager :
  ViewGroupManager<MeshView>(),
  RNSpatialMeshViewManagerInterface<MeshView> {
  private val delegate = RNSpatialMeshViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): MeshView = MeshView(reactContext)

  override fun setPosition(view: MeshView, value: ReadableArray?) {
    if (value != null) {
      view.setPosition(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  override fun setOrientation(view: MeshView, value: ReadableArray?) {
    if (value != null) {
      view.setOrientation(
        value.getDouble(0).toFloat(),
        value.getDouble(1).toFloat(),
        value.getDouble(2).toFloat(),
      )
    }
  }

  override fun setMesh(view: MeshView, value: String?) {
    if (value != null) {
      view.setMesh(value)
    }
  }

  override fun setPositionRelativeToParent(view: MeshView, value: Boolean) {
    view.setPositionRelativeToParent(value)
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> =
    mutableMapOf(
      PositionChangeEvent.NAME to mapOf("registrationName" to "onPositionChange"),
      OrientationChangeEvent.NAME to mapOf("registrationName" to "onOrientationChange"),
    )

  companion object {
    const val NAME = "RNSpatialMeshView"
  }
}
