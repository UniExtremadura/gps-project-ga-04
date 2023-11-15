package es.unex.giiis.fitlife365.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.fitlife365.model.User

@Database(entities = [User::class], version = 1)
abstract class FitLife365Database : RoomDatabase() {
    abstract fun userDao(): UserDao

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
