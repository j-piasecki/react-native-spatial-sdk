package com.jpiasecki.spatialsdk

import com.meta.spatial.core.BooleanAttribute
import com.meta.spatial.core.ComponentBase
import com.meta.spatial.core.ComponentCompanion
import com.meta.spatial.core.IntAttribute

class GrabbableObserverComponent(surfaceId: Int = -1, viewTag: Int = -1, enabled: Boolean = true) : ComponentBase() {
  override fun typeID() = id

  var enabled by BooleanAttribute("enabled", R.string.grabbable_observer_component_enabled, this, enabled)
  var surfaceId by IntAttribute("surfaceId", R.string.grabbable_observer_component_surface_id, this, surfaceId)
  var viewTag by IntAttribute("viewTag", R.string.grabbable_observer_component_view_tag, this, viewTag)
  var active by BooleanAttribute("active", R.string.grabbable_observer_component_is_active, this, false)

  companion object : ComponentCompanion {
    override val id = R.string.grabbable_observer_component_class
    override val createDefaultInstance = { GrabbableObserverComponent() }
  }
}
