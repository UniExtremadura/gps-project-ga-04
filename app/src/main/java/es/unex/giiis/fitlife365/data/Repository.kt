package es.unex.giiis.fitlife365.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import es.unex.giiis.fitlife365.api.APIError
import es.unex.giiis.fitlife365.api.ExerciseAPI
import es.unex.giiis.fitlife365.database.ExerciseModelDao
import es.unex.giiis.fitlife365.database.RoutineDao
import es.unex.giiis.fitlife365.database.UserDao
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User

class Repository constructor(
    private val exerciseModelDao: ExerciseModelDao,
    private val networkService: ExerciseAPI,
    val routineDao: RoutineDao,
    val userDao: UserDao
) {
    private var lastUpdateTimeMillis: Long = 0L

    val exercises = exerciseModelDao.getExercices()

    suspend fun tryUpdateRecentExercicesCache(selectedMuscle :String, difficulty :String) {
        if (shouldUpdateExercicesCache())
            fetchRecentExercises(selectedMuscle, difficulty)
    }

    private suspend fun fetchRecentExercises(selectedMuscle :String, difficulty :String) {
        try {
            // Obtener ejercicios de la API
            val exercisesFromApi =
                getExercisesByMuscleAndDifficulty(selectedMuscle, difficulty).toExercise()

            // Verificar si la lista de ejercicios de la API no está vacía antes de intentar actualizar la base de datos
            // if (exercisesFromApi.isNotEmpty()) {
            // Insertar ejercicios en la base de datos local
            for (exercise in exercisesFromApi)
                exerciseModelDao.insert(exercise)


            // Actualizar el tiempo de la última actualización
            lastUpdateTimeMillis = System.currentTimeMillis()
            //}
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun getExercisesByMuscleAndDifficulty(selectedMuscle :String, difficulty :String): ExerciseList {
        return networkService.getExercisesByMuscleAndDifficulty(selectedMuscle, difficulty)
    }

    fun getExercisesDaoByMuscleAndDifficulty(muscle: String, difficulty: String): LiveData<List<ExerciseModel>> {
        return exerciseModelDao.getExercicesByMuscleAndDifficulty(muscle, difficulty)
    }

    private suspend fun shouldUpdateExercicesCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || exerciseModelDao.getNumberOfExercices() == 0L
    }

    suspend fun insertRoutine(routine: Routine): Long? {
        return routineDao.insert(routine)
    }

     suspend fun getRoutinebyID(routineId: Long?): Routine {
        return routineDao.getRoutineById(routineId)

    }

    suspend fun updateRoutine(routineId: Long?, cadenaEjerciciosNueva: String) {
        routineDao.updateRoutine(routineId, cadenaEjerciciosNueva)

    }

    suspend fun insert(ejercicio: ExerciseModel): Long? {
        return exerciseModelDao.insert(ejercicio)
    }

    suspend fun addRoutineExercise(id: Long?, routineId: Long?) {
        exerciseModelDao.addRoutineExercise(id, routineId)
    }

    suspend fun updateExercise(it: ExerciseModel) {
        exerciseModelDao.updateExercise(it)
    }

    suspend fun deleteRoutine(routineId: Long?) {
        routineDao.deleteRoutine(routineId)
    }

    suspend fun update(sexo: String, edad: Int, altura: Int, peso: Int, userId: Long?) {
         userDao.update(sexo, edad, altura, peso, userId)

    }

      suspend fun getRoutinesByUser(userId: Long?): List<Routine>? {
        return routineDao.getRoutinesByUser(userId)
    }

    suspend fun insertR(routine: Routine): Long? {
        return routineDao.insert(routine)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)

    }

   suspend fun getExerciseById(exerciseId: Long): ExerciseModel {
        return exerciseModelDao.getExerciseById(exerciseId)
    }

    suspend fun getIdByExercise(ejercicio: String): Long? {
        return exerciseModelDao.getIdByExercise(ejercicio)
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            exerciseModelDao: ExerciseModelDao,
            showAPI: ExerciseAPI,
            routineDao: RoutineDao,
            userDao: UserDao
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(exerciseModelDao, showAPI, routineDao, userDao).also { INSTANCE = it }
            }
        }
    }
}
