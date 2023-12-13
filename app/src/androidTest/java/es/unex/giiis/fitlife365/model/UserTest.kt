package es.unex.giiis.fitlife365.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserTest {

    private lateinit var user: User

    @Before
    fun setUp() {
        user = User(
            userId = 1,
            name = "Alba Gomez",
            password = "password123",
            email = "albaGomez@email.com",
            sexo = "Mujer",
            edad = 25,
            altura = 180,
            peso = 75
        )
    }

    @Test
    fun getUserId() {
        assertEquals(1L, user.userId)
    }

    @Test
    fun getName() {
        assertEquals("Alba Gomez", user.name)
    }

    @Test
    fun getPassword() {
        assertEquals("password123", user.password)
    }

    @Test
    fun getEmail() {
        assertEquals("albaGomez@email.com", user.email)
    }

    @Test
    fun getSexo() {
        assertEquals("Mujer", user.sexo)
    }

    @Test
    fun getEdad() {
        assertEquals(25, user.edad)
    }

    @Test
    fun getAltura() {
        assertEquals(180, user.altura)
    }

    @Test
    fun getPeso() {
        assertEquals(75, user.peso)
    }

    @Test
    fun testToString() {
        val expectedToString = "User(userId=1, name=Alba Gomez, password=password123, email=albaGomez@email.com, sexo=Mujer, edad=25, altura=180, peso=75)"
        assertEquals(expectedToString, user.toString())
    }
}
