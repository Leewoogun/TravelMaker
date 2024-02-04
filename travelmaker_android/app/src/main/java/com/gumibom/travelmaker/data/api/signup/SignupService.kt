package com.gumibom.travelmaker.data.api.signup

import com.gumibom.travelmaker.data.dto.request.UserRequestDTO
import com.gumibom.travelmaker.data.dto.response.IsSuccessResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignupService {

    // 이 인증번호가 맞는지 확인하는 api
    @GET("/users/join/auth")
    fun isCertificationNumber(@Query("number") secretNumber : String) : Response<Boolean>

    // TODO 서버에서 보내주는 Response를 보고 구현하기
    // 서버에서 휴대폰 번호를 전송하면 인증번호를 받는 api
    @POST("/users/join/auth")
    fun sendPhoneNumber(@Body phoneNumber : String) : Response<IsSuccessResponseDTO>

    @POST("/users/join/fill/check-id-dup")
    fun checkDuplicatedId(@Body id : String) : Response<IsSuccessResponseDTO>

    @POST("/users/join/fill/check-nick-dup")
    fun checkDuplicatedNickName(@Body name : String) : Response<IsSuccessResponseDTO>

    @POST("/users/join/reg-profile")
    fun saveUserInfo(@Body userInfo : UserRequestDTO) : Response<IsSuccessResponseDTO>



    //아이디와 비밀번호를 입력했을 때 정상적인 유저인지 확인하고 로그인 성공 메시지 발송


}