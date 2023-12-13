package es.unex.giiis.fitlife365.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.unex.giiis.fitlife365.model.ExerciseModel

@Dao
interface ExerciseModelDao {

    @Insert
    suspend fun insert(exerciseModel: ExerciseModel): Long

    @Query("SELECT * FROM ExerciseModel WHERE exerciseId = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long?): ExerciseModel

    @Query("SELECT * FROM ExerciseModel")
    fun getExercices(): LiveData<List<ExerciseModel>>

    @Query("SELECT count(*) FROM ExerciseModel")
    suspend fun getNumberOfExercices(): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllExercices(exercise: List<ExerciseModel>)

    //añadir rutinaId al ejercicio
    @Query("UPDATE ExerciseModel SET routineId = :routineId WHERE exerciseId = :exerciseId")
    suspend fun addRoutineExercise(exerciseId: Long?, routineId: Long?)

    @Update
    suspend fun updateExercise(exercise: ExerciseModel)

}