package com.example.snaccgo.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.snaccgo.model.category.CategoryModel
import com.example.snaccgo.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _snaccCategoryList = mutableStateOf<List<CategoryModel>>(emptyList())
    val snaccCategoryList = _snaccCategoryList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Store the LiveData reference
    private var categoriesLiveData: LiveData<MutableList<CategoryModel>>? = null
    
    // Create an observer instance
    private val categoriesObserver = Observer<MutableList<CategoryModel>> { categories ->
        _snaccCategoryList.value = categories
        _isLoading.value = false
    }

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // Remove previous observer
                categoriesLiveData?.removeObserver(categoriesObserver)
                
                // Get the LiveData from repository
                categoriesLiveData = mainRepository.loadcategory()
                
                // Observe the LiveData
                categoriesLiveData?.observeForever(categoriesObserver)
            } catch (e: Exception) {
                _error.value = "Failed to load categories: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun refreshCategories() {
        loadCategories()
    }
    
    override fun onCleared() {
        super.onCleared()
        // Clean up observer when ViewModel is cleared
        categoriesLiveData?.removeObserver(categoriesObserver)
    }
}