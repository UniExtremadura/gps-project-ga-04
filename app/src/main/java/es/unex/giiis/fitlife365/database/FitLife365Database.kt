package es.unex.giiis.fitlife365.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import es.unex.giiis.fitlife365.model.User

@Database(entities = [User::class], version = 1)
abstract class FitLife365Database : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: FitLife365Database? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Añadir nuevas columnas a la tabla User
                database.execSQL("ALTER TABLE User ADD COLUMN sexo TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE User ADD COLUMN edad INTEGER DEFAULT 0")
                database.execSQL("ALTER TABLE User ADD COLUMN altura INTEGER DEFAULT 0")
                database.execSQL("ALTER TABLE User ADD COLUMN peso INTEGER DEFAULT 0")
            }
        }

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