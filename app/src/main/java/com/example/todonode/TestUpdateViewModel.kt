package com.example.todonode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.domain.repository.TodoRepository
import com.example.todonode.presentation.add_todo_screen.AddTodoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TestUpdateViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {
    private val _updateState = MutableStateFlow(AddTodoUiState())
    val updateState: StateFlow<AddTodoUiState> = _updateState

    fun updateTodo(id: String, todo: TodoRequest) = viewModelScope.launch {
        _updateState.value = AddTodoUiState(isLoading = true)
        try {
            val result = repository.updateTodo(id, todo)
            _updateState.value = AddTodoUiState(response = result)
        } catch (e: Exception) {
//            val errorMessage = if (e is HttpException) {
//                val errorJsonString = e.response()?.errorBody()?.string()
//                val jsonObject = errorJsonString?.let { JSONObject(it) }
//                jsonObject?.getString("message")
//            } else {
//                e.message
//            }
            _updateState.value = AddTodoUiState(error = e.toString() ?: "Unknown error")
        }
    }

}