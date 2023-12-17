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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseModel: ExerciseModel): Long

    @Query("SELECT * FROM ExerciseModel WHERE exerciseId = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long?): ExerciseModel

    @Query("SELECT * FROM ExerciseModel")
    fun getExercices(): LiveData<List<ExerciseModel>>

    @Query("SELECT * FROM ExerciseModel WHERE muscle = :muscle AND difficulty = :difficulty")
    fun getExercicesByMuscleAndDifficulty(muscle: String, difficulty:String): LiveData<List<ExerciseModel>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseModel>)

    @Query("SELECT count(*) FROM ExerciseModel")
    suspend fun getNumberOfExercices(): Long

    //añadir rutinaId al ejercicio
    @Query("UPDATE ExerciseModel SET routineId = :routineId WHERE exerciseId = :exerciseId")
    suspend fun addRoutineExercise(exerciseId: Long?, routineId: Long?)

    @Update
    suspend fun updateExercise(exercise: ExerciseModel)

    @Query("SELECT exerciseId FROM ExerciseModel WHERE name = :name")
    suspend fun getIdByExercise(name: String): Long?


}