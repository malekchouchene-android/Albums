package com.malek.quarn.app.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import com.malek.quarn.AlbumsApplication


val Activity.injector
    get() = (application as AlbumsApplication).appComponent


val Context.isPortrait
    get() = this.resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT




