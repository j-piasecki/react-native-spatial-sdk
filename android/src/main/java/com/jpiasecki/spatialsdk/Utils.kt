package com.jpiasecki.spatialsdk

var nextDisposableID = 0

fun getDisposableId() = nextDisposableID++
