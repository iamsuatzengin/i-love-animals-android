package com.suatzengin.iloveanimals.util.extension

const val ZERO = 0

infix fun Int?.or(other: Int): Int {
    return this ?: other
}
