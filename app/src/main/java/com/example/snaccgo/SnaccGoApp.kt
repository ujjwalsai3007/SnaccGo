package com.example.snaccgo

import android.app.Application
import android.util.Log
import com.example.snaccgo.model.category.CategoryModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.HashMap

class SnaccGoApp : Application() {
    
    companion object {
        private const val TAG = "SnaccGoApp"
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this)
            
            // Enable Firebase database persistence
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
            Log.d(TAG, "Firebase initialized successfully")
            
            // Verify Firebase connectivity and data structure
            verifyFirebaseConnectivity()
            
            // Check if categories exist, if not, add sample data
            checkAndAddSampleCategories()
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Firebase", e)
        }
    }
    
    private fun verifyFirebaseConnectivity() {
        // Test connection to root
        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Firebase root connection successful")
                Log.d(TAG, "Root children: ${snapshot.children.map { it.key }.joinToString()}")
            }
            
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Firebase root connection failed: ${error.message}", error.toException())
            }
        })
        
        // Test connection to "Category" path
        val categoryRef = FirebaseDatabase.getInstance().getReference("Category")
        categoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Category path exists: ${snapshot.exists()}")
                Log.d(TAG, "Category children count: ${snapshot.childrenCount}")
                snapshot.children.forEach { child ->
                    Log.d(TAG, "Category child: ${child.key}, value: ${child.value}")
                }
            }
            
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Category check failed: ${error.message}", error.toException())
            }
        })
        
        // Also try lowercase "category" path
        val lowercaseCategoryRef = FirebaseDatabase.getInstance().getReference("category")
        lowercaseCategoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "lowercase category path exists: ${snapshot.exists()}")
                Log.d(TAG, "lowercase category children count: ${snapshot.childrenCount}")
            }
            
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "lowercase category check failed: ${error.message}", error.toException())
            }
        })
    }
    
    private fun checkAndAddSampleCategories() {
        val categoryRef = FirebaseDatabase.getInstance().getReference("Category")
        
        categoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists() || snapshot.childrenCount == 0L) {
                    Log.d(TAG, "No categories found, adding sample data")
                    addSampleCategories(categoryRef)
                } else {
                    Log.d(TAG, "Categories already exist, not adding sample data")
                }
            }
            
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error checking categories", error.toException())
            }
        })
    }
    
    private fun addSampleCategories(categoryRef: DatabaseReference) {
        val sampleCategories = listOf(
            CategoryModel(
                title = "Juices",
                id = 0,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857119/juices_j4mxfg.png"
            ),
            CategoryModel(
                title = "Sauces",
                id = 1,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857115/sauces_jadzg8.png"
            ),
            CategoryModel(
                title = "Pizza",
                id = 2,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857115/pizza_rgsv55.png"
            ),
            CategoryModel(
                title = "Noodles",
                id = 3,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857115/noodles_od8xg9.png"
            ),
            CategoryModel(
                title = "Donut",
                id = 4,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857114/donut_steg94.png"
            ),
            CategoryModel(
                title = "Icecreams",
                id = 5,
                picUrl = "https://res.cloudinary.com/dj0ei8l4j/image/upload/v1743857114/icecreams_vd7clp.png"
            )
        )
        
        // Also add to lowercase path to be sure
        val lowercaseCategoryRef = FirebaseDatabase.getInstance().getReference("category")
        
        sampleCategories.forEachIndexed { index, category ->
            // Add to Category (uppercase)
            categoryRef.child(index.toString()).setValue(category)
                .addOnSuccessListener {
                    Log.d(TAG, "Added category to Category/$index: ${category.title}")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to add category to Category/$index", e)
                }
                
            // Add to category (lowercase)
            lowercaseCategoryRef.child(index.toString()).setValue(category)
                .addOnSuccessListener {
                    Log.d(TAG, "Added category to category/$index: ${category.title}")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to add category to category/$index", e)
                }
        }
    }
} 