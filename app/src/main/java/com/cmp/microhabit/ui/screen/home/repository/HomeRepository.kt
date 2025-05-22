package com.cmp.microhabit.ui.screen.home.repository

import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    fun addHabitLog(
        userId: String,
        habitId: String,
        date: String,
        completed: Boolean,
        onResult: (Boolean) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val log = HabitLog(
            date = date,
            completed = completed,
            timestamp = System.currentTimeMillis()
        )
        db.collection("users")
            .document(userId)
            .collection("habits")
            .document(habitId)
            .collection("logs")
            .document(date)
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
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(20)
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