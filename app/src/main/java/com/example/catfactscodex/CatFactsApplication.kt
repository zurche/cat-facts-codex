package com.example.catfactscodex

import android.app.Application
import com.example.catfactscodex.di.AppContainer
import com.example.catfactscodex.di.DefaultAppContainer

class CatFactsApplication : Application() {
    val container: AppContainer by lazy { DefaultAppContainer() }
}
