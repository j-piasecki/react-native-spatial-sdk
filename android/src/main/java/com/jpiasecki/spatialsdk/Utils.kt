package com.jpiasecki.spatialsdk

import com.meta.spatial.core.Vector3
import kotlin.math.abs

var nextDisposableID = 0

fun getDisposableId() = nextDisposableID++

fun Vector3.almostEquals(other: Vector3, epsilon: Float = 0.0001f): Boolean = abs(other.x - x) <= epsilon && abs(other.y - y) <= epsilon && abs(other.y - y) <= epsilon
