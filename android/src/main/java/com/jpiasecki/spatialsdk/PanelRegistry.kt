package com.jpiasecki.spatialsdk

import com.facebook.react.interfaces.fabric.ReactSurface
import com.meta.spatial.core.Entity

object PanelRegistry {
  private val panels: MutableMap<Int, PanelData> = HashMap()

  fun registerPanel(id: Int, data: PanelData) {
    panels[id] = data
  }

  fun unregisterPanel(id: Int) {
    panels.remove(id)
  }

  fun getPanel(id: Int) = panels[id]
}

data class PanelData(
  val entryPoint: String,
  val surface: ReactSurface,
  val entity: Entity? = null
)
