package com.example.snaccgo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.snaccgo.model.category.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val TAG = "MainRepository"
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    
    // Try setting the URL explicitly
    init {
        try {
            firebaseDatabase.setPersistenceEnabled(true)
            Log.d(TAG, "Firebase database initialized with persistence enabled")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting persistence", e)
        }
    }

    fun loadcategory(): LiveData<MutableList<CategoryModel>> {
        val categoryData = MutableLiveData<MutableList<CategoryModel>>()
        
        // Set initial value
        categoryData.value = mutableListOf()
        
        // Try multiple paths to find the data
        checkPath("Category", categoryData)
        checkPath("category", categoryData)
        checkPath("", categoryData) // Check root for structure
        
        return categoryData
    }
    
    private fun checkPath(path: String, categoryData: MutableLiveData<MutableList<CategoryModel>>) {
        val ref = if (path.isEmpty()) firebaseDatabase.reference else firebaseDatabase.getReference(path)
        
        Log.d(TAG, "Checking path: ${ref.toString()}")
        
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "Data received for path '$path', exists: ${snapshot.exists()}, children count: ${snapshot.childrenCount}")
                
                if (path.isEmpty()) {
                    // For root path, just log the structure
                    snapshot.children.forEach { child ->
                        Log.d(TAG, "Root child: ${child.key}, has children: ${child.hasChildren()}")
                    }
                    return
                }
                
                val lists = mutableListOf<CategoryModel>()
                for (childSnapShot in snapshot.children) {
                    try {
                        Log.d(TAG, "Processing data in '$path': key=${childSnapShot.key}, value=${childSnapShot.value}")
                        
                        // Try different ways to extract data
                        val item = childSnapShot.getValue(CategoryModel::class.java)
                        if (item != null) {
                            Log.d(TAG, "Category successfully parsed from '$path': ${item.title}")
                            lists.add(item)
                        } else {
                            Log.w(TAG, "Failed to parse using CategoryModel from '$path': ${childSnapShot.key}")
                            
                            // Try manual parsing
                            try {
                                val title = childSnapShot.child("title").getValue(String::class.java) 
                                    ?: childSnapShot.child("Title").getValue(String::class.java)
                                    ?: ""
                                
                                val id = childSnapShot.child("id").getValue(Int::class.java) 
                                    ?: childSnapShot.child("Id").getValue(Int::class.java)
                                    ?: childSnapShot.child("ID").getValue(Int::class.java)
                                    ?: 0
                                
                                val picUrl = childSnapShot.child("picUrl").getValue(String::class.java)
                                    ?: childSnapShot.child("PicUrl").getValue(String::class.java)
                                    ?: childSnapShot.child("picurl").getValue(String::class.java)
                                    ?: childSnapShot.child("Picurl").getValue(String::class.java)
                                    ?: childSnapShot.child("pic_url").getValue(String::class.java)
                                    ?: ""
                                
                                Log.d(TAG, "Manual parsing from '$path' - id: $id, title: $title, picUrl: $picUrl")
                                
                                if (title.isNotEmpty()) {
                                    lists.add(CategoryModel(title = title, id = id, picUrl = picUrl))
                                    Log.d(TAG, "Manually parsed category added from '$path': $title")
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error parsing manually from '$path': ${childSnapShot.key}", e)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing item from '$path': ${childSnapShot.key}", e)
                    }
                }
                
                Log.d(TAG, "Total categories loaded from '$path': ${lists.size}")
                
                if (lists.isNotEmpty()) {
                    // Only update the LiveData if we found categories in this path
                    categoryData.value = lists
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to load data from '$path': ${error.message}", error.toException())
            }
        })
    }
}
//
//The code structure for fetching category data from Firebase is correct
//You're properly converting the snapshot to your data model
//You're using LiveData to return the results, which is a good pattern
