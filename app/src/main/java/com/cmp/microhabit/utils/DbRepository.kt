package com.cmp.microhabit.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object DbRepository {

    fun getCurrentUser(db: FirebaseFirestore, userId: String): DocumentReference {
        return db.collection("users").document(userId)
    }

    fun getHabitsList(db: FirebaseFirestore, userId: String): CollectionReference {
        return getCurrentUser(db, userId).collection("habits")
    }

    fun getHabitLogs(db: FirebaseFirestore, userId: String, habitId: String): DocumentReference {
        return getHabitsList(db, userId).document("habit_$habitId").collection("habit_info").document("logs")
    }

    fun getHabitStatistics(db: FirebaseFirestore, userId: String, habitId: String): DocumentReference {
        return getHabitsList(db, userId).document("habit_$habitId").collection("habit_info").document("statistics")
    }
}