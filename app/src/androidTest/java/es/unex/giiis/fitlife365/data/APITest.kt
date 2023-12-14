import es.unex.giiis.fitlife365.api.ExerciseAPI
import es.unex.giiis.fitlife365.data.Exercise
import es.unex.giiis.fitlife365.data.ExerciseList
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response
import retrofit2.Retrofit

class APITest {

    @Test
    fun testgetExercisesByMuscle() = runBlocking {
        // Crear un objeto simulado de ExerciseAPI con Mockito
        val exerciseAPIMock = mock(ExerciseAPI::class.java)

        // Configurar el objeto simulado para que devuelva una respuesta específica
        val exerciseList = ExerciseList()
        exerciseList.add(
            Exercise(
                name = "Exercise 1",
                type = "type 1",
                muscle = "biceps",
                equipment = "equipment 1",
                difficulty = "difficulty 1",
                instructions = "instructions 1"
            )
        )

        // Aquí puedes personalizar la respuesta según tus necesidades
        `when`(exerciseAPIMock.getExercisesByMuscle("biceps")).thenReturn(exerciseList)

        // Configurar Retrofit para usar el objeto simulado
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/") // No importa la URL, ya que se está utilizando el objeto simulado
            .build()

        // Utilizar el objeto simulado en la clase de prueba
        val service: ExerciseAPI = retrofit.create(ExerciseAPI::class.java)
        val result = service.getExercisesByMuscle("biceps")

        // Verificar que la solicitud se haya realizado correctamente
        assertEquals(result.size, 1)
        assertEquals(result[0].name, "Exercise 1")
        assertEquals(result[0].type, "type 1")
        assertEquals(result[0].muscle, "biceps")
        assertEquals(result[0].equipment, "equipment 1")
        assertEquals(result[0].difficulty, "difficulty 1")
        assertEquals(result[0].instructions, "instructions 1")
    }
}
