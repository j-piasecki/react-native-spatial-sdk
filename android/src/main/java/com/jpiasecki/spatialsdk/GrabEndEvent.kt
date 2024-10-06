package com.jpiasecki.spatialsdk

import com.facebook.react.uimanager.events.Event

class GrabEndEvent(surfaceId: Int, viewTag: Int) : Event<GrabEndEvent>(surfaceId, viewTag) {
  override fun getEventName() = NAME

  // all events can be coalesced
  override fun getCoalescingKey(): Short = 0

  companion object {
    const val NAME = "topEnd"
  }
}
