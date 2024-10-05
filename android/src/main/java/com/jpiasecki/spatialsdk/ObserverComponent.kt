package com.jpiasecki.spatialsdk

import com.meta.spatial.core.BooleanAttribute
import com.meta.spatial.core.ComponentBase
import com.meta.spatial.core.ComponentCompanion
import com.meta.spatial.core.IntAttribute
import com.meta.spatial.core.Vector3
import com.meta.spatial.core.Vector3Attribute

class ObserverComponent(surfaceId: Int = -1, viewTag: Int = -1, enabled: Boolean = true) : ComponentBase() {
  override fun typeID() = id

  var enabled by BooleanAttribute("enabled", R.string.observer_component_enabled, this, enabled)
  var surfaceId by IntAttribute("surfaceId", R.string.observer_component_surface_id, this, surfaceId)
  var viewTag by IntAttribute("viewTag", R.string.observer_component_view_tag, this, viewTag)
  var previousPosition by Vector3Attribute("previousPosition", R.string.observer_component_last_position, this, Vector3(0f))
  var previousOrientation by Vector3Attribute("previousOrientation", R.string.observer_component_last_orientation, this, Vector3(0f))

  companion object : ComponentCompanion {
    override val id = R.string.observer_component_class
    override val createDefaultInstance = { ObserverComponent() }
  }
}
