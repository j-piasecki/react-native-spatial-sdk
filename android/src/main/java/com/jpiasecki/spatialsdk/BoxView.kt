package com.jpiasecki.spatialsdk

import android.net.Uri
import android.view.ViewGroup
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.views.view.ReactViewGroup
import com.meta.spatial.core.Entity
import com.meta.spatial.core.Pose
import com.meta.spatial.core.Quaternion
import com.meta.spatial.core.Vector3
import com.meta.spatial.toolkit.Box
import com.meta.spatial.toolkit.Color4
import com.meta.spatial.toolkit.Material
import com.meta.spatial.toolkit.Mesh
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.toolkit.TransformParent

class BoxView(private val reactContext: ReactContext) :
  ReactViewGroup(reactContext),
  EntityHolder {
  private var panelId = -1
  override var entity: Entity? = null
    private set

  private var width = 1f
  private var height = 1f
  private var depth = 1f

  private var position = Vector3(0f, 1.3f, 2f)
  private var orientation = Quaternion(0f, 0f, 0f)

  private var positionRelativeToParent = true

  private fun makeBox() = Box(
    Vector3(-width / 2, -height / 2, -depth / 2),
    Vector3(width / 2, height / 2, depth / 2),
  )

  fun setPosition(x: Float, y: Float, z: Float) {
    this.position = Vector3(x, y, z)

    entity?.let {
      val transform = it.getComponent<Transform>()
      transform.transform.t = this.position
      it.setComponent(transform)

      val observer = it.getComponent<ObserverComponent>()
      observer.previousPosition = this.position
      it.setComponent(observer)
    }
  }

  fun setOrientation(pitch: Float, yaw: Float, roll: Float) {
    this.orientation = Quaternion(pitch, yaw, roll)

    entity?.let {
      val transform = it.getComponent<Transform>()
      transform.transform.q = this.orientation
      it.setComponent(transform)

      val observer = it.getComponent<ObserverComponent>()
      observer.previousOrientation = Vector3(pitch, yaw, roll)
      it.setComponent(observer)
    }
  }

  fun setWidth(width: Float) {
    this.width = width
    entity?.setComponent(makeBox())
  }

  fun setHeight(height: Float) {
    this.height = height
    entity?.setComponent(makeBox())
  }

  fun setDepth(depth: Float) {
    this.depth = depth
    entity?.setComponent(makeBox())
  }

  fun setPositionRelativeToParent(value: Boolean) {
    positionRelativeToParent = value
    val parentEntity = if (positionRelativeToParent) findParentEntity() else Entity.nullEntity()

    entity?.setComponent(TransformParent(parentEntity))
  }

  private fun findParentEntity(): Entity {
    var current = this.parent as? ViewGroup

    while (current != null && current !is EntityHolder) {
      current = current.parent as? ViewGroup
    }

    return (current as? EntityHolder)?.entity ?: Entity.nullEntity()
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()

    val parentEntity = if (positionRelativeToParent) findParentEntity() else Entity.nullEntity()

    entity = Entity.create(
      Mesh(Uri.parse("mesh://box")),
      makeBox(),
      Transform(Pose(position, orientation)),
      ObserverComponent(UIManagerHelper.getSurfaceId(this), this.id),
      Material().apply {
        baseColor = Color4(red = 1.0f, green = 0.1f, blue = 0.1f, alpha = 1.0f)
      },
      TransformParent(parentEntity),
    )

    tryAttachComponents(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    PanelRegistry.getPanel(panelId)?.stopSurface()
  }
}
