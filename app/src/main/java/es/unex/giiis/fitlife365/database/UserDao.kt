package es.unex.giiis.fitlife365.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.fitlife365.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Insert
    suspend fun insert(user: User): Long

    @Query("UPDATE user SET sexo = :sexo, edad = :edad, altura = :altura, peso = :peso WHERE userId = :userId")
    suspend fun update(sexo: String, edad: Int, altura: Int, peso: Int, userId: Long?)

    @Query("UPDATE user SET name = :name, sexo = :sexo, edad = :edad, altura = :altura, peso = :peso WHERE userId = :userId")
    suspend fun updateUser(
        userId: Long,
        name: String,
        sexo: String,
        edad: Int,
        altura: Int,
        peso: Int
    )
}
