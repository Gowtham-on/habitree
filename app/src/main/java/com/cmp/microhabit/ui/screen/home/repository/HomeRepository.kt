package com.cmp.microhabit.ui.screen.home.repository

import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    fun addHabitLog(
        userId: String,
        habitId: String,
        date: String,
        dateString: String,
        completed: Boolean,
        onResult: (Boolean) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val log = HabitLog(
            habitId = habitId.toLong(),
            date = date,
            dateString = dateString,
            completed = completed,
            timestamp = System.currentTimeMillis()
        )
        db.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .collection("logs")
            .document(dateString)
            .set(log)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(false)
            }
    }

    fun getHabitLogs(
        userId: String,
        habitId: String,
        onResult: (List<HabitLog>?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .collection("logs")
            .get()
            .addOnSuccessListener { snapshot ->
                val logs = snapshot.documents.mapNotNull { it.toObject(HabitLog::class.java) }

                onResult(logs)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(null)
            }
    }

    fun getLogsFromPreviousSundayToToday(
        userId: String,
        habitId: String,
        startDate: String,
        endDate: String,
        onResult: (List<HabitLog>?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()


        db.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .collection("logs")
            .limit(15)
            .get()
            .addOnSuccessListener { snapshot ->
                val logs = snapshot.documents.mapNotNull { it.toObject(HabitLog::class.java) }
                onResult(logs)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(null)
            }
    }


}