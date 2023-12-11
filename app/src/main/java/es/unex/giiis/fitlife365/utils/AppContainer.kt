package es.unex.giiis.fitlife365.utils

import android.content.Context
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.database.FitLife365Database

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val database = FitLife365Database.getInstance(context!!)
    val repository = Repository(db!!.userDao(),db.routineDao(),db.exerciseModelDao(),getNetworkService())}
