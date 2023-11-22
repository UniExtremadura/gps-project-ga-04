package es.unex.giiis.fitlife365.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.fitlife365.model.ExerciseModel

@Dao
interface ExerciseModelDao {

    @Insert
    suspend fun insert(exerciseModel: ExerciseModel): Long

    @Query("SELECT * FROM ExerciseModel")
    suspend fun getAll(): List<ExerciseModel>

}