package com.jpiasecki.spatialsdk

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class SpatialSdkPackage : TurboReactPackage() {
  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> =
    listOf(
      PanelViewManager(),
      GrabbableViewManager(),
      BoxViewManager(),
      MaterialViewManager(),
      ScaleViewManager(),
      MeshViewManager(),
    )

  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
    if (name == PanelModule.NAME) {
      PanelModule(reactContext)
    } else {
      null
    }

  override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
    mapOf(
      PanelModule.NAME to ReactModuleInfo(
        PanelModule.NAME,
        PanelModule.NAME,
        false, // canOverrideExistingModule
        false, // needsEagerInit
        false, // isCxxModule
        true, // isTurboModule
      ),
    )
  }
}
