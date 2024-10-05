package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.meta.spatial.core.Vector3

class OrientationChangeEvent(surfaceId: Int, viewTag: Int, private val orientation: Vector3) : Event<OrientationChangeEvent>(surfaceId, viewTag) {
  override fun getEventName() = NAME

  // all events can be coalesced
  override fun getCoalescingKey(): Short = 0

  override fun getEventData(): WritableMap = Arguments.createMap().apply {
    putDouble("pitch", orientation.x.toDouble())
    putDouble("yaw", orientation.y.toDouble())
    putDouble("roll", orientation.z.toDouble())
  }

  companion object {
    const val NAME = "topOrientationChange"
  }
}
