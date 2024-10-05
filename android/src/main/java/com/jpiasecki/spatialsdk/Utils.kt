package com.jpiasecki.spatialsdk

var NextDisposableID = 0

fun getDisposableId() = NextDisposableID++
