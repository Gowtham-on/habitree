package com.cmp.microhabit.ui.screen.onboarding.repository

import com.cmp.microhabit.ui.screen.onboarding.model.UserData
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitStoppingReason
import com.cmp.microhabit.utils.DbRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class OnboardingRepository @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(
        habitStoppingReason: List<HabitStoppingReason>,
        onResult: (Boolean, Int?) -> Unit
    ) {
        val counterRef = db.collection("counter").document("users")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(counterRef)
            val lastId = snapshot.getLong("lastUserId") ?: 0
            val newId = lastId + 1

            val userData = UserData(
                id = newId,
                userName = "User $newId",
                habitStoppingReason = habitStoppingReason,
            )

            val userRef = db.collection("users").document(newId.toString())
            transaction.set(userRef, userData)
            transaction.update(counterRef, "lastUserId", newId)
            newId.toInt()
        }.addOnSuccessListener { newUserId ->
            onResult(true, newUserId)
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
            onResult(false, null)
        }
    }

    fun getUserData(userId: String, onResult: (data: UserData?) -> Unit) {
        DbRepository.getCurrentUser(db, userId).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val userData = document.toObject(UserData::class.java)
                onResult(userData)
            } else {
                onResult(null)
            }
        }
    }
}