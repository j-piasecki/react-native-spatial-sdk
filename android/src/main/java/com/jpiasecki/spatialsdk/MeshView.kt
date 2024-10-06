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
import com.meta.spatial.toolkit.Mesh
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.toolkit.TransformParent

class MeshView(private val reactContext: ReactContext) :
  ReactViewGroup(reactContext),
  EntityHolder {
  override var entity: Entity? = null
    private set

  private var mesh: String? = null

  private var position = Vector3(0f, 1.3f, 2f)
  private var orientation = Quaternion(0f, 0f, 0f)

  private var positionRelativeToParent = true

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
      observer.previousOrientation = this.orientation.toEuler()
      it.setComponent(observer)
    }
  }

  fun setMesh(mesh: String) {
    this.mesh = mesh
    entity?.setComponent(Mesh(Uri.parse(mesh)))
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
      Transform(Pose(position, orientation)),
      ObserverComponent(UIManagerHelper.getSurfaceId(this), this.id),
      TransformParent(parentEntity),
    )

    if (mesh != null) {
      entity!!.setComponent(Mesh(Uri.parse(mesh)))
    }

    tryAttachComponents(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    entity?.destroy()
  }
}
