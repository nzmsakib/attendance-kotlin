package com.nasakib.attendancems.data

import android.content.Context
import android.util.Log
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.data.model.LoggedInUser
import com.nasakib.attendancems.data.model.LoggedOutUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(context: Context) {
    private var _gotUser = false
    fun setGotUser(gotUser: Boolean) {
        _gotUser = gotUser
    }
    private var _user: LoggedInUser? = null
    fun setUser(user: LoggedInUser) {
        _user = user
    }

    private var sessionManager: SessionManager
    private var apiClient: ApiClient
    private val context: Context = context

    init {
        sessionManager = SessionManager(context)
        apiClient = ApiClient()
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val apiService = apiClient.getApiService(context)
            GlobalScope.launch {
                val result = apiService.login(LoggedOutUser(username, password)).execute()
                if (result.isSuccessful) {
                    val response = result.body()
                    if (response != null) {
                        Log.d("LoginDataSource", "login: $response")
                        if (response.status == "success") {
                            setUser(response.data[0])
                            sessionManager.saveAuthToken(response.data[0].token)
                        } else {
                            Log.d("LoginDataSource", "login: ${response.message}")
                        }
                    } else {
                        Log.d("LoginDataSource", "login: ${result.errorBody()}")
                    }
                    setGotUser(true)
                } else {
                    Log.d("LoginDataSource", "login: ${result.errorBody()}")
                    setGotUser(true)
                }
            }
            while (!_gotUser) {
                Thread.sleep(100)
            }
            if (_user != null) {
                Result.Success(_user!!)
            } else {
                Result.Error(IOException("Error logging in"))
            }
        } catch (e: Throwable) {
            Log.d("LoginDataSource", "login: $e")
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun login(): Result<LoggedInUser> {
        return try {
            val apiService = apiClient.getApiService(context)
            GlobalScope.launch {
                val result = apiService.getUser().execute()
                if (result.isSuccessful) {
                    val response = result.body()
                    if (response != null) {
                        Log.d("LoginDataSource", "login: $response")
                        if (response.status == "success") {
                            setUser(response.data[0])
                            sessionManager.saveAuthToken(response.data[0].token)
                        } else {
                            Log.d("LoginDataSource", "login: ${response.message}")
                        }
                    } else {
                        Log.d("LoginDataSource", "login: ${result.errorBody()}")
                    }
                    setGotUser(true)
                } else {
                    Log.d("LoginDataSource", "login: ${result.errorBody()}")
                    setGotUser(true)
                }
            }
            while (!_gotUser) {
                Thread.sleep(100)
            }
            if (_user != null) {
                Result.Success(_user!!)
            } else {
                Result.Error(IOException("Error logging in"))
            }
        } catch (e: Throwable) {
            Log.d("LoginDataSource", "login: $e")
            Result.Error(IOException("Error logging in", e))
        }
    }
}