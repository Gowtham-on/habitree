package com.cmp.microhabit.ui.screen.home.repository

import android.util.Log
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.ui.screen.onboarding.model.Statistics
import com.cmp.microhabit.ui.screen.onboarding.model.UserHabit
import com.cmp.microhabit.utils.DbRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HomeRepository @Inject constructor() {
    val db = FirebaseFirestore.getInstance()

    fun getHabitList(userId: String, onResult: (List<UserHabit>) -> Unit) {
        DbRepository.getHabitsList(db, userId)
            .get()
            .addOnSuccessListener { result ->
                val habitList = result.documents.mapNotNull { it.toObject(UserHabit::class.java) }
                onResult(habitList)
            }
    }

    fun addLog(userId: String, habitId: String, habitLog: HabitLog) {
        DbRepository.getHabitLogs(db, userId, habitId).update("dateLogs", habitLog.dateLogs)
    }

    fun getRecentLogs(
        userId: String,
        habitId: String,
        onResult: (HabitLog?) -> Unit
    ) {
        DbRepository.getHabitLogs(db, userId, habitId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.toObject(HabitLog::class.java)
                    onResult(userData)
                } else {
                    onResult(null)
                }
            }.addOnFailureListener {
                Log.d("ErrorLog", it.message.toString())
                onResult(null)
            }
    }

    fun getHabitStatistics(userId: String, habitId: String, onResult: (Statistics?) -> Unit) {
        DbRepository.getHabitStatistics(db, userId, habitId)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val statistics = it.toObject(Statistics::class.java)
                    onResult(statistics)
                } else {
                    onResult(null)
                }
            }.addOnFailureListener {
                Log.d("ErrorLog", it.message.toString())
                onResult(null)
            }
    }
}