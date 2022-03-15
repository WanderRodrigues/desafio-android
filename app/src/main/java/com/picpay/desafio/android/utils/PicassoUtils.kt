package com.picpay.desafio.android.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.picpay.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PicassoUtils {

    fun loadImage(img: String, imageView: ImageView, progress: ProgressBar){
        Picasso.get()
            .load(img)
            .error(R.drawable.ic_round_account_circle)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progress.visibility = View.GONE
                    Log.e("Exception Image", e?.message)
                }
            })
    }
}