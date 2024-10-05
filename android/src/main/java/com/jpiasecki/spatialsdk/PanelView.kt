package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.ReactContext
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.core.Entity
import com.meta.spatial.core.Pose
import com.meta.spatial.core.Quaternion
import com.meta.spatial.core.Vector3
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.toolkit.createPanelEntity

class PanelView(reactContext: ReactContext) : ReactViewGroup(reactContext) {
  private var panelId = -1
  private var entity: Entity? = null

  fun setPanelId(id: Int) {
    panelId = id
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()

    entity = Entity.createPanelEntity(
      panelId,
      Transform(Pose(Vector3(0f, 1.3f, 2f), Quaternion(0f, 0f, 0f))),
      Grabbable()
    )
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()

    entity!!.destroy()
  }
}
