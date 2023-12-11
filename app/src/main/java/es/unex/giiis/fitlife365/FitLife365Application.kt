package es.unex.giiis.fitlife365

import android.app.Application
import es.unex.giiis.fitlife365.utils.AppContainer

class FitLife365Application : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
