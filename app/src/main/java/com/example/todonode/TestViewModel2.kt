package com.example.todonode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.domain.repository.TodoRepository
import com.example.todonode.pref.UserPref
import com.example.todonode.presentation.home_screen.HomeUiState
import com.example.todonode.presentation.login_screen.LoginUiState
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
class TestViewModel2 @Inject constructor(
    private val repository: TodoRepository,
    private val userPref: UserPref
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState

    val getUser = userPref.getUserToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )


    init {
        getUserAuth()
    }

    private fun getUserAuth() = viewModelScope.launch {
        _loginState.value = LoginUiState(isLoading = true)
        try {

            getUser.collectLatest {
                if(it.isEmpty()){
                    return@collectLatest
                }
                val result = repository.getUserAuth(token = it)
                _loginState.value = LoginUiState(user = result.user)
            }

        }  catch (e: Exception) {
            val errorMessage = if (e is HttpException) {
                val errorJsonString = e.response()?.errorBody()?.string()
                val jsonObject = errorJsonString?.let { JSONObject(it) }
                jsonObject?.getString("message")
            } else {
                e.message
            }
            _loginState.value = LoginUiState(error = errorMessage ?: "Unknown error")
        }
    }

    fun loginUser(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginState.value = LoginUiState(isLoading = true)
        try {
            val result = repository.loginUser(loginRequest)
            userPref.saveUserToken(result.token)
            _loginState.value = LoginUiState(response = result)
        } catch (e: Exception) {
            val errorMessage = if (e is HttpException) {
                val errorJsonString = e.response()?.errorBody()?.string()
                val jsonObject = errorJsonString?.let { JSONObject(it) }
                jsonObject?.getString("message")
            } else {
                e.message
            }
            _loginState.value = LoginUiState(error = errorMessage ?: "Unknown error")
        }
    }

    fun logoutUser() = viewModelScope.launch {
        userPref.clearUserToken()
    }
}