package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReactContext
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.core.Entity
import com.meta.spatial.core.Pose
import com.meta.spatial.core.Quaternion
import com.meta.spatial.core.Vector3
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.toolkit.createPanelEntity

class PanelView(reactContext: ReactContext) : ReactViewGroup(reactContext), EntityHolder {
  private var panelId = -1
  private var data: PanelData? = null
  override val entity: Entity?
    get() = data?.entity

  private var position = Vector3(0f, 1.3f, 2f)
  private var orientation = Quaternion(0f, 0f, 0f)

  fun setPanelId(id: Int) {
    panelId = id
    data = PanelRegistry.getPanel(panelId)
  }

  fun setPosition(x: Float, y: Float, z: Float) {
    this.position = Vector3(x, y, z)

    entity?.let {
      val transform = it.getComponent<Transform>()
      transform.transform.t = this.position
      it.setComponent(transform)
    }
  }

  fun setOrientation(pitch: Float, yaw: Float, roll: Float) {
    this.orientation = Quaternion(pitch, yaw, roll)

    entity?.let {
      val transform = it.getComponent<Transform>()
      transform.transform.q = this.orientation
      it.setComponent(transform)
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    data?.entity = Entity.createPanelEntity(
      panelId,
      Transform(Pose(position, orientation)),
    )

    tryAttachComponents(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    PanelRegistry.getPanel(panelId)?.stopSurface()
  }
}
