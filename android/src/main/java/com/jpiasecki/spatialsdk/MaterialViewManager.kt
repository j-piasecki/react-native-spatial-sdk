package com.jpiasecki.spatialsdk

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.viewmanagers.RNSpatialMaterialViewManagerDelegate
import com.facebook.react.viewmanagers.RNSpatialMaterialViewManagerInterface

@ReactModule(name = GrabbableViewManager.NAME)
class MaterialViewManager :
  ViewGroupManager<MaterialView>(),
  RNSpatialMaterialViewManagerInterface<MaterialView> {
  private val delegate = RNSpatialMaterialViewManagerDelegate(this)

  override fun getDelegate() = delegate
  override fun getName() = NAME

  override fun createViewInstance(reactContext: ThemedReactContext): MaterialView = MaterialView(reactContext)

  override fun setColor(view: MaterialView, value: Int?) {
    view.setColor(value ?: Color.BLACK)
  }

  companion object {
    const val NAME = "RNSpatialMaterialView"
  }
}
