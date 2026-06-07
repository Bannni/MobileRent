package com.example.rentalapkgamesyaban.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalapkgamesyaban.data.entity.UserEntity
import com.example.rentalapkgamesyaban.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = userRepository.login(email, pass)
            if (user != null) {
                _currentUser.value = user
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Invalid email or password")
            }
        }
    }

    fun register(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = UserEntity(name = name, email = email, password = pass, role = "buyer")
            val success = userRepository.register(user)
            if (success) {
                _authState.value = AuthState.Registered
            } else {
                _authState.value = AuthState.Error("Email already registered")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    object Registered : AuthState()
    data class Error(val message: String) : AuthState()
}
