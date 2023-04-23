package com.nasakib.attendancems.apis

import com.nasakib.attendancems.data.model.ApiResult
import com.nasakib.attendancems.data.model.LoggedInUser
import com.nasakib.attendancems.data.model.LoggedOutUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {
     @POST("login")
     fun checkUser(
          @Body user: LoggedOutUser,
          @Header("Accept") accept: String = "application/json"
     ): Call<ApiResult<LoggedInUser>>
}