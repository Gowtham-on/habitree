package com.cmp.microhabit.ui.screen.home.repository

import android.util.Log
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.ui.screen.onboarding.model.Statistics
import com.cmp.microhabit.ui.screen.onboarding.model.StreakChartDetails
import com.cmp.microhabit.ui.screen.onboarding.model.UserHabit
import com.cmp.microhabit.utils.DbRepository
import com.cmp.microhabit.utils.RepositoryType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

    fun getStreakChartData(
        userId: String,
        habitId: String,
        onResult: (StreakChartDetails?) -> Unit
    ) {
        DbRepository.getChartDetails(db, userId, habitId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.toObject(StreakChartDetails::class.java)
                    onResult(userData)
                } else {
                    onResult(null)
                }
            }.addOnFailureListener {
                Log.d("ErrorLog", it.message.toString())
                onResult(null)
            }
    }

    fun completeTask(
        habitId: String,
        userId: String,
        date: String,
        isCompleted: Boolean,
        currentStreak: Int,
        bestStreak: Int,
        noOfTimesCompleted: Int,
        habitLogs: Map<String, HabitLog>,
        onResult: (updated: Boolean, type: RepositoryType) -> Unit
    ) {
        habitLogs.toMutableMap()[habitId]?.dateLogs?.toMutableMap()[date] = isCompleted

        val dateLogs = habitLogs[habitId]?.dateLogs?.toMutableMap()
        dateLogs?.set(date, isCompleted)
        val collectionRef = DbRepository.getHabitInfoCollection(db, userId, habitId)
        // update Logs
        collectionRef.document("logs").update("dateLogs", dateLogs ?: mapOf<String, Boolean>())
            .addOnSuccessListener {
                onResult(true, RepositoryType.LOG)
            }.addOnFailureListener {
                onResult(false, RepositoryType.LOG)
            }

        collectionRef.document("statistics").update(mapOf(
            "currentStreak" to currentStreak + 1,
            "noOfTimesCompleted" to noOfTimesCompleted + 1,
            "bestStreak" to bestStreak
        ))

        collectionRef.document("chartDetails").set(
            mapOf("streaks" to mapOf(date to currentStreak + 1)),
            SetOptions.merge()
        )
    }
}