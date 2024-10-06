package com.jpiasecki.spatialsdk

import com.facebook.react.ReactApplication
import com.facebook.react.uimanager.UIManagerHelper
import com.meta.spatial.core.Query
import com.meta.spatial.core.SystemBase
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.Transform

class ObserverSystem(private val reactApplication: ReactApplication) : SystemBase() {
  override fun execute() {
    val reactContext = reactApplication.reactHost?.currentReactContext ?: return
    val transformObserversQuery = Query.where { has(ObserverComponent.id, Transform.id) }

    for (entity in transformObserversQuery.eval()) {
      val observer = entity.getComponent<ObserverComponent>()
      val transform = entity.getComponent<Transform>()

      if (!observer.enabled) {
        continue
      }

      if (!transform.transform.t.almostEquals(observer.previousPosition)) {
        observer.previousPosition = transform.transform.t
        entity.setComponent(observer)

        val event = PositionChangeEvent(observer.surfaceId, observer.viewTag, transform.transform.t)
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, observer.viewTag)
        dispatcher?.dispatchEvent(event)
      }

      val eulerOrientation = transform.transform.q.toEuler()
      if (!eulerOrientation.almostEquals(observer.previousOrientation)) {
        observer.previousOrientation = eulerOrientation
        entity.setComponent(observer)

        val event = OrientationChangeEvent(observer.surfaceId, observer.viewTag, eulerOrientation)
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, observer.viewTag)
        dispatcher?.dispatchEvent(event)
      }
    }

    val grabbableObserversQuery = Query.where { has(Grabbable.id, GrabbableObserverComponent.id) }

    for (entity in grabbableObserversQuery.eval()) {
      val observer = entity.getComponent<GrabbableObserverComponent>()
      val grabbable = entity.getComponent<Grabbable>()

      if (!observer.enabled) {
        continue
      }

      if (grabbable.isGrabbed != observer.active) {
        observer.active = grabbable.isGrabbed
        entity.setComponent(observer)

        val event = if (grabbable.isGrabbed) {
          GrabStartEvent(
            observer.surfaceId,
            observer.viewTag,
          )
        } else {
          GrabEndEvent(observer.surfaceId, observer.viewTag)
        }
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, observer.viewTag)
        dispatcher?.dispatchEvent(event)
      }
    }
  }
}
