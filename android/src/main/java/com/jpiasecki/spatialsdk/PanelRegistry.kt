package com.jpiasecki.spatialsdk

import com.facebook.react.interfaces.fabric.ReactSurface
import com.meta.spatial.core.Entity

object PanelRegistry {
  private val panels: MutableMap<Int, PanelData> = HashMap()

  fun registerPanel(id: Int, data: PanelData) {
    panels[id] = data
  }

  fun getPanel(id: Int) = panels[id]
}

data class PanelData(
  val entryPoint: String,
  var entity: Entity? = null
) {
  var reactSurface: ReactSurface? = null

  fun stopSurface() {
    reactSurface?.let {
      it.stop()
      it.detach()
    }
    entity?.destroy()

    reactSurface = null
  }
}
