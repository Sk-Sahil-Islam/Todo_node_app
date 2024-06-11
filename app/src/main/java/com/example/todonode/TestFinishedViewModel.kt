package com.example.todonode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonode.domain.repository.TodoRepository
import com.example.todonode.pref.UserPref
import com.example.todonode.presentation.home_screen.HomeUiState
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
class TestFinishedViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val userPref: UserPref
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())

    val state: StateFlow<HomeUiState> = _state
    val token = userPref.getUserToken().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    init {
        getFinishedTodos()
    }

    private fun getFinishedTodos() = viewModelScope.launch {
        _state.value = HomeUiState(isLoading = true)
        try {
            token.collectLatest {
                if(it.isEmpty()){
                    return@collectLatest
                }
                val result = repository.getFinishedTodos(token = it)
                _state.value = HomeUiState(todos = result)
            }
        } catch (e: Exception) {
            val errorMessage = if (e is HttpException) {
                val errorJsonString = e.response()?.errorBody()?.string()
                val jsonObject = errorJsonString?.let { JSONObject(it) }
                jsonObject?.getString("message")
            } else {
                e.message
            }
            _state.value = HomeUiState(error = errorMessage ?: "Unknown error")
        }
    }

}