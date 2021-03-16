package com.pha.likaijie.bingingAdapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.pha.likaijie.R
import com.squareup.picasso.Picasso

/**
 *Create by LiKaiJie
 *Date:2020/12/6
 */
class BindingAdapter {

    companion object{

        @JvmStatic
        @BindingAdapter(value = ["image", "defaultImageResource"], requireAll = false)
        fun setImage(imageView: ImageView, imageUrl: String?, imageResource: Int) {
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get().load(imageUrl).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(imageView)
            } else {
                imageView.setImageResource(imageResource)
            }
        }
    }
}