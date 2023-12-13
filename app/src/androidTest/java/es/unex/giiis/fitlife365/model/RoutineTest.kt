package es.unex.giiis.fitlife365.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RoutineTest {

    private lateinit var routine: Routine

    @Before
    fun setUp() {
        // Inicializa el objeto Routine antes de cada test
        routine = Routine(
            routineId = 1,
            userId = 1,
            name = "Monday Routine",
            pesoObjetivo = 70,
            diasEntrenamiento = "Monday",
            ejercicios = "1, 2, 3"
        )
    }

    @Test
    fun getRoutineId() {
        assertEquals(1L, routine.routineId)
    }

    @Test
    fun getUserId() {
        assertEquals(1L, routine.userId)
    }

    @Test
    fun getName() {
        assertEquals("Monday Routine", routine.name)
    }

    @Test
    fun getPesoObjetivo() {
        assertEquals(70, routine.pesoObjetivo)
    }

    @Test
    fun getDiasEntrenamiento() {
        assertEquals("Monday", routine.diasEntrenamiento)
    }

    @Test
    fun getEjercicios() {
        assertEquals("1, 2, 3", routine.ejercicios)
    }

    @Test
    fun testToString() {
        val expectedToString = "Routine(routineId=1, userId=1, name=Monday Routine, pesoObjetivo=70, diasEntrenamiento=Monday, ejercicios=1, 2, 3)"
        assertEquals(expectedToString, routine.toString())
    }
}
