package br.com.cwi.marvelapp.presentation.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resource, this, attachToRoot)
}

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}