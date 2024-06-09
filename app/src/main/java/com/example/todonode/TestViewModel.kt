package com.example.todonode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.domain.repository.TodoRepository
import com.example.todonode.pref.UserPref
import com.example.todonode.presentation.signup_screen.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val userPref: UserPref
) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState

    fun registerUser(registerRequest: RegisterRequest) = viewModelScope.launch {
        _registerState.value = RegisterUiState(isLoading = true)

        try {
            val result = repository.registerUser(registerRequest)
            userPref.saveUserToken(result.token)
            _registerState.value = RegisterUiState(response = result)
        } catch (e: Exception) {
            val errorMessage = if (e is HttpException) {
                val errorJsonString = e.response()?.errorBody()?.string()
                val jsonObject = errorJsonString?.let { JSONObject(it) }
                jsonObject?.getString("message")
            } else {
                e.message
            }
            _registerState.value = RegisterUiState(error = errorMessage ?: "Unknown error")
        }
    }
}