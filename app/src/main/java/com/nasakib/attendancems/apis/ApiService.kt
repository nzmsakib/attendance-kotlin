package com.nasakib.attendancems.apis

import com.nasakib.attendancems.data.model.ApiResult
import com.nasakib.attendancems.data.model.LoggedInUser
import com.nasakib.attendancems.data.model.LoggedOutUser
import com.nasakib.attendancems.data.model.Report
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interface for defining REST request functions
 */
interface ApiService {
    @POST("login")
    fun login(
        @Body user: LoggedOutUser,
    ): Call<ApiResult<LoggedInUser>>

    @GET("logout")
    fun logout(): Call<ApiResult<LoggedInUser>>

    @GET("user")
    fun getUser(): Call<ApiResult<LoggedInUser>>

    @GET("student/{student}")
    fun getStudentHome(
        @Path("student") student: Int,
    ) : Call<ApiResult<Report>>
}