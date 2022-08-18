package com.timplifier.boilerplate.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

fun formatCurrentUserTime(dateFormat: String): String =
    SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())

