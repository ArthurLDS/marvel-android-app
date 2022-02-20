package br.com.cwi.marvelapp.presentation.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import br.com.cwi.marvelapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/*    imageUrl: String?,
    @DrawableRes placeholderRes: Int = R.drawable.design_ic_product_placeholder,
    @DrawableRes imageErrorRes: Int = R.drawable.design_ic_product_placeholder,
    forceCache: Boolean = false
) {
    val requestOptions = RequestOptions().apply {
        placeholder(placeholderRes)
        error(imageErrorRes)
    }

    val cacheStrategy = if (forceCache) DiskCacheStrategy.ALL else DiskCacheStrategy.AUTOMATIC

    this.tag = null
    try {
        Glide.with(this.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .diskCacheStrategy(cacheStrategy)
            .into(this)
    } catch (ex: Exception) {
        println(ex.message)
    }
}*/