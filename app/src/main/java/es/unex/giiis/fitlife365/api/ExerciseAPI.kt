package es.unex.giiis.fitlife365.api

import android.telecom.Call
import es.unex.giiis.fitlife365.data.Exercise
import es.unex.giiis.fitlife365.data.ExerciseList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.api-ninjas.com/v1/exercises?X-Api-Key=wfr49mRVMN8mvCFkbYj5Jw==kfHUsZc2rKbpMCJT&difficulty=beginner
private const val API_KEY = "X-Api-Key=wfr49mRVMN8mvCFkbYj5Jw==kfHUsZc2rKbpMCJT"

private val service: ExerciseAPI by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(ExerciseAPI::class.java)
}

fun getNetworkService() = service

class APIError(message: String, cause: Throwable?) : Throwable(message, cause)

interface ExerciseAPI {
    @GET("exercises?" + API_KEY + "&")
    suspend fun getExercisesByMuscle(@Query("muscle") muscle: String): ExerciseList

    @GET("exercises?" + API_KEY + "&")
    suspend fun getExercisesByEquipment(@Query("equipment") equipment: String): ExerciseList

    @GET("exercises?" + API_KEY + "&")
    suspend fun getExercisesByDifficulty(@Query("difficulty") difficulty: String): ExerciseList

    @GET("exercises?" + API_KEY + "&")
    suspend fun getExercisesByType(@Query("type") type: String): ExerciseList

    @GET("exercises?" + API_KEY + "&")
    suspend fun getExercisesByMuscleAndDifficulty(@Query("muscle") muscle: String, @Query("difficulty") difficulty: String): ExerciseList

    @GET("exercises?" + API_KEY + "&")
    suspend fun getAllExercises(): ExerciseList


}
