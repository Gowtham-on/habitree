package com.cmp.microhabit.ui.screen.onboarding.repository

import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.ui.screen.onboarding.model.UserData
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitPreferenceTime
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitStoppingReason
import com.google.firebase.firestore.FirebaseFirestore

class OnboardingRepository {
    private val db = FirebaseFirestore.getInstance()

    fun saveUserData(
        habitPref: List<HabitSelection>,
        habitStoppingReason: List<HabitStoppingReason>,
        habitPrefTime: HabitPreferenceTime,
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
                habitPreference = habitPref,
                habitStoppingReason = habitStoppingReason,
                habitPrefTime = habitPrefTime
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
}