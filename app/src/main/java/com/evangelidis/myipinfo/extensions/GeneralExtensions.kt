package com.evangelidis.myipinfo.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Int.isEven(): Boolean = isMultipleOf(2)

fun Int.isOdd(): Boolean = !isEven()

fun Int.isMultipleOf(n: Int) = this % n == 0

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)
