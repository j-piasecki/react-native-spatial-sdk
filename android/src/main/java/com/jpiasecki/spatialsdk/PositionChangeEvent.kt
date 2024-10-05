package com.jpiasecki.spatialsdk

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.meta.spatial.core.Vector3

class PositionChangeEvent(surfaceId: Int, viewTag: Int, private val position: Vector3) : Event<PositionChangeEvent>(surfaceId, viewTag) {
  override fun getEventName() = NAME

  // all events can be coalesced
  override fun getCoalescingKey(): Short = 0

  override fun getEventData(): WritableMap = Arguments.createMap().apply {
    putDouble("x", position.x.toDouble())
    putDouble("y", position.y.toDouble())
    putDouble("z", position.z.toDouble())
  }

  companion object {
    const val NAME = "topPositionChange"
  }
}
