package es.unex.giiis.fitlife365.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giiis.fitlife365.model.Routine

@Dao
interface RoutineDao {

    @Insert
    suspend fun insert(routine: Routine): Long

    @Query("SELECT * FROM Routine WHERE userId = :userId")
    suspend fun getRoutinesByUser(userId: Long?): List<Routine>

    @Query("UPDATE Routine SET ejercicios = :exercises WHERE routineId = :routineId")
    suspend fun updateRoutine(routineId: Long?, exercises: String)

    @Query("SELECT * FROM Routine")
    fun getRoutines(): LiveData<List<Routine>>

    @Query("SELECT count(*) FROM Routine")
    suspend fun getNumberOfRoutines(): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRoutines(routine: List<Routine>)


    @Query("DELETE FROM Routine WHERE routineId = :routineId")
    suspend fun deleteRoutine(routineId: Long?)

    @Query("SELECT * FROM Routine WHERE routineId = :routineId")
    suspend fun getRoutineById(routineId: Long?): Routine

}
