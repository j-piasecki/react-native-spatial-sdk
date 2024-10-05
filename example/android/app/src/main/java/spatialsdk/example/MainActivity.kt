package spatialsdk.example

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.ReactApplication
import com.facebook.react.ReactDelegate
import com.facebook.react.bridge.Callback
import com.facebook.react.interfaces.fabric.ReactSurface
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener

class MainActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler, PermissionAwareActivity {
  private var permissionsCallback: Callback? = null
  private var permissionListener: PermissionListener? = null
  private lateinit var reactSurface: ReactSurface
  private val reactDelegate: ReactDelegate by lazy {
    val app = application as ReactApplication
    ReactDelegate(this, app.reactHost, getMainComponentName(), null)
  }

  fun getMainComponentName(): String = "SpatialSdkExample"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as ReactApplication
    reactSurface = app.reactHost?.createSurface(this, getMainComponentName(), null)!!

    setContentView(reactSurface.view)
    reactSurface.start()
  }

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

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    return reactDelegate.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
  }

  override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
    return reactDelegate.shouldShowDevMenuOrReload(keyCode, event) || super.onKeyUp(keyCode, event)
  }

  override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
    return reactDelegate.onKeyLongPress(keyCode) || super.onKeyLongPress(keyCode, event)
  }

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
    listener: PermissionListener?
  ) {
    this.permissionListener = listener
    this.requestPermissions(permissions, requestCode)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String?>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    this.permissionsCallback =
      Callback { args: Array<out Any?> ->
        if (this.permissionListener?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
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
