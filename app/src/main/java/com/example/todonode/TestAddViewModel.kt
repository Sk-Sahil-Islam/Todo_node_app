package com.example.todonode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.domain.repository.TodoRepository
import com.example.todonode.pref.UserPref
import com.example.todonode.presentation.add_todo_screen.AddTodoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TestAddViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val userPref: UserPref
) : ViewModel() {

    private val _addTodoState = MutableStateFlow(AddTodoUiState())
    val addTodoState: StateFlow<AddTodoUiState> = _addTodoState

    private val token = userPref.getUserToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    fun addTodo(todo: TodoRequest) = viewModelScope.launch {
        _addTodoState.value = AddTodoUiState(isLoading = true)
        try {
            token.collectLatest {
                if(it.isEmpty()){
                    return@collectLatest
                }
                val result = repository.addTodo(it, todo)
                _addTodoState.value = AddTodoUiState(response = result)
            }
        } catch (e: Exception) {
//            val errorMessage = if (e is HttpException) {
//                val errorJsonString = e.response()?.errorBody()?.string()
//                var message: String? = null
//                if (errorJsonString != null) {
//                    try {
//                        val jsonObject = JSONObject(errorJsonString)
//                        message = jsonObject.optString("message", null)
//                    } catch (jsonException: JSONException) {
//                        _addTodoState.value = AddTodoUiState(error = jsonException.toString())
//                    }
//                }
//                message
//            } else {
//                e.message
//            }
            val errorMessage = if (e is HttpException) {
                val errorJsonString = e.response()?.errorBody()?.string()
                val jsonObject = errorJsonString?.let { JSONObject(it) }
                jsonObject?.getString("message")
            } else {
                e.message
            }
            _addTodoState.value = AddTodoUiState(error = errorMessage ?: "Unknown error")
        }
    }


}