package es.unex.giiis.fitlife365.data

import es.unex.giiis.fitlife365.data.Exercise
import es.unex.giiis.fitlife365.model.ExerciseModel

fun ExerciseList.toExercise(): List<ExerciseModel> {
    return mapNotNull { exercise ->
        ExerciseModel(
            name = exercise.name.orEmpty(),
            type = exercise.type.orEmpty(),
            muscle = exercise.muscle.orEmpty(),
            equipment = exercise.equipment.orEmpty(),
            difficulty = exercise.difficulty.orEmpty(),
            instructions = exercise.instructions.orEmpty()
        )
    }
}