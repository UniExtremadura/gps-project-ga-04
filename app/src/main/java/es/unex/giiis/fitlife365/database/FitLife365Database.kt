package es.unex.giiis.fitlife365.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User


@Database(entities = [User::class, Routine::class], version = 1, exportSchema = false)
abstract class FitLife365Database : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun routineDao(): RoutineDao
    companion object {
        private var INSTANCE: FitLife365Database? = null


        fun getInstance(context: Context): FitLife365Database? {
            if (INSTANCE == null) {
                synchronized(FitLife365Database::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        FitLife365Database::class.java, "fitlife365.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}