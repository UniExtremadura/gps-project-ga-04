package es.unex.giiis.fitlife365.data

import es.unex.giiis.fitlife365.api.ExerciseAPI
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class APITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var exerciseAPI: ExerciseAPI

    @Before
    fun setup() {
        // Inicializa el servidor simulado
        mockWebServer = MockWebServer()

        // Configura la URL base del servidor simulado en tu API
        exerciseAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseAPI::class.java)
    }

    @Test
    fun testGetExerciseByMuscle() = runBlocking {
        mockWebServer = MockWebServer()

        // Configura la URL base del servidor simulado en tu API
        exerciseAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseAPI::class.java)
        // Define una respuesta simulada con un ejercicio específico para el bíceps
        val mockResponse = MockResponse()
            .setBody("{\"exerciseList\": [{\"id\": 1, \"name\": \"Curl de biceps\", \"muscle\": \"biceps\", \"equipment\": \"barbell\", \"difficulty\": \"beginner\", \"type\": \"strength\", \"instructions\": \"Intrucciones}]}")

        // Agrega la respuesta al servidor simulado
        mockWebServer.enqueue(mockResponse)


        // Realiza la llamada a la API con el músculo especificado y verifica que la respuesta sea correcta
        val result = exerciseAPI.getExercisesByMuscle("biceps")
        assertEquals(1, result.size)

        // Verifica que el ejercicio devuelto sea el esperado
        val exercise = result[0]
        assertEquals("Curl de biceps", exercise.name)
        assertEquals("biceps", exercise.muscle)
        assertEquals("barbell", exercise.equipment)
        assertEquals("beginner", exercise.difficulty)
        assertEquals("strength", exercise.type)
        assertEquals("Intrucciones", exercise.instructions)

        mockWebServer.shutdown()
    }


    @Test
    fun testGetExerciseByMuscleAndDifficulty (){
        // Define una respuesta simulada con un ejercicio específico para el bíceps y dificultad beginner

        val mockResponse = MockResponse()
            .setBody("{\"exerciseList\": [{\"id\": 1, \"name\": \"Curl de biceps\", \"muscle\": \"biceps\", \"equipment\": \"barbell\", \"difficulty\": \"beginner\", \"type\": \"strength\", \"instructions\": \"Intrucciones}]}")

        // Agrega la respuesta al servidor simulado
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            // Realiza la llamada a la API con el músculo especificado y verifica que la respuesta sea correcta
            val result = exerciseAPI.getExercisesByMuscleAndDifficulty("biceps", "beginner")
            assertEquals(1, result.size)

            // Verifica que el ejercicio devuelto sea el esperado
            val exercise = result[0]
            assertEquals("Curl de biceps", exercise.name)
            assertEquals("biceps", exercise.muscle)
            assertEquals("barbell", exercise.equipment)
            assertEquals("beginner", exercise.difficulty)
            assertEquals("strength", exercise.type)
            assertEquals("Intrucciones", exercise.instructions)
        }
    }

    @Test
    fun testGetExerciseByEquipment (){
        // Define una respuesta simulada con un ejercicio específico para el bíceps y dificultad beginner

        val mockResponse = MockResponse()
            .setBody("{\"exerciseList\": [{\"id\": 1, \"name\": \"Curl de biceps\", \"muscle\": \"biceps\", \"equipment\": \"barbell\", \"difficulty\": \"beginner\", \"type\": \"strength\", \"instructions\": \"Intrucciones}]}")


        // Agrega la respuesta al servidor simulado
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            // Realiza la llamada a la API con el músculo especificado y verifica que la respuesta sea correcta
            val result = exerciseAPI.getExercisesByEquipment("barbell")
            assertEquals(1, result.size)

            // Verifica que el ejercicio devuelto sea el esperado
            val exercise = result[0]
            assertEquals("Curl de biceps", exercise.name)
            assertEquals("biceps", exercise.muscle)
            assertEquals("barbell", exercise.equipment)
            assertEquals("beginner", exercise.difficulty)
            assertEquals("strength", exercise.type)
            assertEquals("Intrucciones", exercise.instructions)
        }
    }

    @Test
    fun testGetExerciseByDifficulty (){
        // Define una respuesta simulada con un ejercicio específico para el bíceps y dificultad beginner

        val mockResponse = MockResponse()
            .setBody("{\"exerciseList\": [{\"id\": 1, \"name\": \"Curl de biceps\", \"muscle\": \"biceps\", \"equipment\": \"barbell\", \"difficulty\": \"beginner\", \"type\": \"strength\", \"instructions\": \"Intrucciones}]}")

        // Agrega la respuesta al servidor simulado
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            // Realiza la llamada a la API con el músculo especificado y verifica que la respuesta sea correcta
            val result = exerciseAPI.getExercisesByDifficulty("beginner")
            assertEquals(1, result.size)

            // Verifica que el ejercicio devuelto sea el esperado
            val exercise = result[0]
            assertEquals("Curl de biceps", exercise.name)
            assertEquals("biceps", exercise.muscle)
            assertEquals("barbell", exercise.equipment)
            assertEquals("beginner", exercise.difficulty)
            assertEquals("strength", exercise.type)
            assertEquals("Intrucciones", exercise.instructions)
        }
    }

    @Test
    fun testGetExerciseByType (){
        // Define una respuesta simulada con un ejercicio específico para el bíceps y dificultad beginner

        val mockResponse = MockResponse()
            .setBody("{\"exerciseList\": [{\"id\": 1, \"name\": \"Curl de biceps\", \"muscle\": \"biceps\", \"equipment\": \"barbell\", \"difficulty\": \"beginner\", \"type\": \"strength\", \"instructions\": \"Intrucciones}]}")

        // Agrega la respuesta al servidor simulado
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            // Realiza la llamada a la API con el músculo especificado y verifica que la respuesta sea correcta
            val result = exerciseAPI.getExercisesByType("strength")
            assertEquals(1, result.size)

            // Verifica que el ejercicio devuelto sea el esperado
            val exercise = result[0]
            assertEquals("Curl de biceps", exercise.name)
            assertEquals("biceps", exercise.muscle)
            assertEquals("barbell", exercise.equipment)
            assertEquals("beginner", exercise.difficulty)
            assertEquals("strength", exercise.type)
            assertEquals("Intrucciones", exercise.instructions)
        }
    }
}
