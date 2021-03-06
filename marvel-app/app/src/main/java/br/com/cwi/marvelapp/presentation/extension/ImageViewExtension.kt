package br.com.cwi.marvelapp.presentation.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import br.com.cwi.marvelapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(
    imageUrl: String?,
    @DrawableRes placeholderRes: Int = R.drawable.ic_image_placeholder,
    @DrawableRes imageErrorRes: Int = R.drawable.ic_image_placeholder
) {
    val requestOptions = RequestOptions().apply {
        placeholder(placeholderRes)
        error(imageErrorRes)
        transform(CenterCrop())
    }
    Glide.with(this.context)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .into(this)
}
