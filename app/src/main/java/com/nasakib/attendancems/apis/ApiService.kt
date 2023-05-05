package com.nasakib.attendancems.apis

import com.nasakib.attendancems.apis.Constants.LOGIN_URL
import com.nasakib.attendancems.apis.Constants.STUDENT_CLASSROOM_URL
import com.nasakib.attendancems.apis.Constants.STUDENT_HOME_URL
import com.nasakib.attendancems.apis.Constants.TEACHER_ATTENDANCE_URL
import com.nasakib.attendancems.apis.Constants.TEACHER_CLASSROOM_URL
import com.nasakib.attendancems.apis.Constants.TEACHER_HOME_URL
import com.nasakib.attendancems.apis.Constants.USER_URL
import com.nasakib.attendancems.data.model.ApiResult
import com.nasakib.attendancems.data.model.Attendance
import com.nasakib.attendancems.data.model.Classroom
import com.nasakib.attendancems.data.model.EnrollAttendance
import com.nasakib.attendancems.data.model.LoggedInUser
import com.nasakib.attendancems.data.model.LoggedOutUser
import com.nasakib.attendancems.data.model.StudentClassroomReport
import com.nasakib.attendancems.data.model.StudentDashboardReport
import com.nasakib.attendancems.data.model.TeacherAttendanceResponse
import com.nasakib.attendancems.data.model.TeacherClassroomResponse
import com.nasakib.attendancems.data.model.TeacherDashboardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interface for defining REST request functions
 */
interface ApiService {
    @POST(LOGIN_URL)
    fun login(
        @Body user: LoggedOutUser,
    ): Call<ApiResult<LoggedInUser>>

    @GET("logout")
    fun logout(): Call<ApiResult<LoggedInUser>>

    @GET(USER_URL)
    fun getUser(): Call<ApiResult<LoggedInUser>>

    @GET(STUDENT_HOME_URL)
    fun getStudentHome() : Call<ApiResult<StudentDashboardReport>>

    @GET(STUDENT_CLASSROOM_URL)
    fun getStudentClassroom(
        @Path("classroom") classroom: Int
    ) : Call<ApiResult<StudentClassroomReport>>

    @GET(TEACHER_HOME_URL)
    fun getTeacherHome() : Call<ApiResult<TeacherDashboardResponse>>

    @POST(TEACHER_HOME_URL)
    fun createClassroom(
        @Body classroom: Classroom
    ) : Call<ApiResult<Any>>

    @GET(TEACHER_CLASSROOM_URL)
    fun getTeacherClassroom(
        @Path("classroom") classroom: Int
    ) : Call<ApiResult<TeacherClassroomResponse>>

    @POST(TEACHER_CLASSROOM_URL)
    fun createAttendance(
        @Path("classroom") classroom: Int,
        @Body attendance: Attendance
    ) : Call<ApiResult<Any>>

    @GET(TEACHER_ATTENDANCE_URL)
    fun getTeacherAttendance(
        @Path("attendance") attendance: Int
    ) : Call<ApiResult<TeacherAttendanceResponse>>

    @POST(TEACHER_ATTENDANCE_URL)
    fun enrollAttendance(
        @Path("attendance") attendance: Int,
        @Body enrollAttendance: EnrollAttendance
    ) : Call<ApiResult<Any>>
}