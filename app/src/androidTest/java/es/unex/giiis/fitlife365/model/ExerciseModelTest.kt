package es.unex.giiis.fitlife365.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExerciseModelTest {

    private lateinit var exerciseModel: ExerciseModel

    @Before
    fun setUp() {
        // Inicializa el objeto ExerciseModel antes de cada test
        exerciseModel = ExerciseModel(
            exerciseId = 1,
            name = "Push-up",
            type = "Strength",
            muscle = "Chest",
            equipment = "None",
            difficulty = "Intermediate",
            instructions = "Do a push-up",
            isSelected = false,
            routineId = 1,
            isCompleted = false
        )
    }

    @Test
    fun getExerciseId() {
        assertEquals(1L, exerciseModel.exerciseId)
    }

    @Test
    fun getName() {
        assertEquals("Push-up", exerciseModel.name)
    }

    @Test
    fun getType() {
        assertEquals("Strength", exerciseModel.type)
    }

    @Test
    fun getMuscle() {
        assertEquals("Chest", exerciseModel.muscle)
    }

    @Test
    fun getEquipment() {
        assertEquals("None", exerciseModel.equipment)
    }

    @Test
    fun getDifficulty() {
        assertEquals("Intermediate", exerciseModel.difficulty)
    }

    @Test
    fun getInstructions() {
        assertEquals("Do a push-up", exerciseModel.instructions)
    }

    @Test
    fun getIsSelected() {
        assertEquals(false, exerciseModel.isSelected)
    }

    @Test
    fun getRoutineId() {
        assertEquals(1L, exerciseModel.routineId)
    }

    @Test
    fun getIsCompleted() {
        assertEquals(false, exerciseModel.isCompleted)
    }

    @Test
    fun testToString() {
        val expectedToString = "ExerciseModel(exerciseId=1, name=Push-up, type=Strength, muscle=Chest, equipment=None, difficulty=Intermediate, instructions=Do a push-up, isSelected=false, routineId=1, isCompleted=false)"
        assertEquals(expectedToString, exerciseModel.toString())
    }
}
