package com.jpiasecki.spatialsdk

import com.facebook.react.ReactApplication
import com.facebook.react.uimanager.UIManagerHelper
import com.meta.spatial.core.Query
import com.meta.spatial.core.SystemBase
import com.meta.spatial.toolkit.Transform

class ObserverSystem(private val reactApplication: ReactApplication) : SystemBase() {
  override fun execute() {
    val reactContext = reactApplication.reactHost?.currentReactContext ?: return
    val query = Query.where { has(ObserverComponent.id, Transform.id) }

    for (entity in query.eval()) {
      val observer = entity.getComponent<ObserverComponent>()
      val transform = entity.getComponent<Transform>()

      if (!observer.enabled) {
        continue
      }

      if (!transform.transform.t.almostEquals(observer.previousPosition)) {
        observer.previousPosition = transform.transform.t

        val event = PositionChangeEvent(observer.surfaceId, observer.viewTag, transform.transform.t)
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, observer.viewTag)
        dispatcher?.dispatchEvent(event)
      }

      val eulerOrientation = transform.transform.q.toEuler()
      if (!eulerOrientation.almostEquals(observer.previousOrientation)) {
        observer.previousOrientation = eulerOrientation

        val event = OrientationChangeEvent(observer.surfaceId, observer.viewTag, eulerOrientation)
        val dispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, observer.viewTag)
        dispatcher?.dispatchEvent(event)
      }

      entity.setComponent(observer)
    }
  }
}
