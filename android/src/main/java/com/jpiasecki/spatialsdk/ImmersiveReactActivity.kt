package com.jpiasecki.spatialsdk

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import com.facebook.react.ReactApplication
import com.facebook.react.ReactDelegate
import com.facebook.react.bridge.Callback
import com.facebook.react.interfaces.fabric.ReactSurface
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener
import com.meta.spatial.core.Entity
import com.meta.spatial.core.Pose
import com.meta.spatial.core.Quaternion
import com.meta.spatial.core.SpatialFeature
import com.meta.spatial.core.Vector3
import com.meta.spatial.runtime.ReferenceSpace
import com.meta.spatial.toolkit.AppSystemActivity
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.PanelRegistration
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.toolkit.createPanelEntity
import com.meta.spatial.vr.VRFeature

open class ImmersiveReactActivity :
  AppSystemActivity(),
  DefaultHardwareBackBtnHandler,
  PermissionAwareActivity {
  private lateinit var reactSurface: ReactSurface

  private var permissionsCallback: Callback? = null
  private var permissionListener: PermissionListener? = null

  private val reactDelegate: ReactDelegate by lazy {
    val app = application as ReactApplication
    ReactDelegate(this, app.reactHost, getMainComponentName(), null)
  }

  protected open fun getMainComponentName(): String = "App"

  override fun registerFeatures(): List<SpatialFeature> = listOf(VRFeature(this))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as ReactApplication

    reactSurface = app.reactHost!!.createSurface(this, getMainComponentName(), null)!!
    reactSurface.start()

    componentManager.registerComponent<ObserverComponent>(ObserverComponent.Companion)

    systemManager.registerSystem(ObserverSystem(app))
  }

  override fun onSceneReady() {
    super.onSceneReady()
    scene.setViewOrigin(0.0f, 0.0f, 0.0f)
    scene.enableHolePunching(true)
    scene.setReferenceSpace(ReferenceSpace.LOCAL_FLOOR)

    Entity.createPanelEntity(
      R.id.main_panel,
      Transform(Pose(Vector3(-2f, 1.3f, 2f), Quaternion(0f, -40f, 0f))),
      Grabbable(),
    )
  }

  override fun registerPanels(): List<PanelRegistration> = listOf(
    PanelRegistration(R.id.main_panel) {
      config {
        height = 1.2f
        width = 1.6f
        enableLayer = true
      }
      view {
        reactSurface.view!!
      }
    },
  )

  override fun onResume() {
    super.onResume()
    reactDelegate.onHostResume()

    this.permissionsCallback?.let {
      it.invoke(*arrayOfNulls<Any>(0))
      this.permissionsCallback = null
    }
  }

  override fun onPause() {
    super.onPause()
    reactDelegate.onHostPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    reactDelegate.onHostDestroy()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    reactDelegate.onActivityResult(requestCode, resultCode, data, true)
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean = reactDelegate.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)

  override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean = reactDelegate.shouldShowDevMenuOrReload(keyCode, event) || super.onKeyUp(keyCode, event)

  override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean = reactDelegate.onKeyLongPress(keyCode) || super.onKeyLongPress(keyCode, event)

  override fun onBackPressed() {
    if (!reactDelegate.onBackPressed()) {
      super.onBackPressed()
    }
  }

  override fun invokeDefaultOnBackPressed() {
    super.onBackPressed()
  }

  override fun onNewIntent(intent: Intent?) {
    if (!reactDelegate.onNewIntent(intent)) {
      super.onNewIntent(intent)
    }
  }

  override fun requestPermissions(
    permissions: Array<String?>,
    requestCode: Int,
    listener: PermissionListener?,
  ) {
    this.permissionListener = listener
    this.requestPermissions(permissions, requestCode)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String?>,
    grantResults: IntArray,
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    this.permissionsCallback =
      Callback { args: Array<out Any?> ->
        if (this.permissionListener?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
          ) == true
        ) {
          this.permissionListener = null
        }
      }
  }

  override fun onWindowFocusChanged(hasFocus: Boolean) {
    super.onWindowFocusChanged(hasFocus)
    reactDelegate.onWindowFocusChanged(hasFocus)
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    reactDelegate.onConfigurationChanged(newConfig)
  }
}
