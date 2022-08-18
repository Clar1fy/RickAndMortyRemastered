package com.timplifier.boilerplate.presentation.extensions

inline fun <reified T : Any> T.className(): String = this::class.java.simpleName