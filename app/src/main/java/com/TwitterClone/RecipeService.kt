package com.TwitterClone

import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object RecipeService {
    private val db = FirebaseFirestore.getInstance()
    fun getVeri (recipes : SnapshotStateList<Veri>){
        db.collection("recipes").get().addOnSuccessListener {
            recipes.updateList(it.toObjects(Veri::class.java))
        }.addOnFailureListener {
            recipes.updateList(listOf())
        }
    }
}