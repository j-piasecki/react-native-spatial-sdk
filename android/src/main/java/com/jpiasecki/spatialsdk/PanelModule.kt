package com.jpiasecki.spatialsdk

import com.facebook.react.ReactApplication
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.meta.spatial.toolkit.PanelRegistration

class PanelModule(reactContext: ReactApplicationContext) : NativeSpatialPanelModuleSpec(reactContext) {

  override fun getName() = NAME

  @ReactMethod
  override fun registerPanel(entryPoint: String, config: ReadableMap): Double {
    val id = getDisposableId()

    val panelWidth = config.getDouble("width").toFloat()
    val panelHeight = config.getDouble("height").toFloat()

    val activity = reactApplicationContext.currentActivity as ImmersiveReactActivity
    val app = activity.application as ReactApplication
    val reactSurface = app.reactHost?.createSurface(activity, entryPoint, null)!!
    reactSurface.start()

    activity.registerPanel(PanelRegistration(id) {
      config {
        width = panelWidth
        height = panelHeight
        enableLayer = true
        enableTransparent = true
      }
      view {
        reactSurface.view!!
      }
    })

    PanelRegistry.registerPanel(id, PanelData(entryPoint, reactSurface))

    return id.toDouble()
  }

  override fun unregisterPanel(panelId: Double) {
    PanelRegistry.getPanel(panelId.toInt())?.let {
      it.surface.stop()
      it.surface.detach()
    }
    PanelRegistry.unregisterPanel(panelId.toInt())
  }

  companion object {
    const val NAME = "RNSpatialPanelModule"
  }
}
