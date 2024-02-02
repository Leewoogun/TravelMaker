package com.gumibom.travelmaker.data.repository.signup

import com.gumibom.travelmaker.data.dto.request.UserRequestDTO
import retrofit2.Response

interface SignupRepository {

    suspend fun sendPhoneNumber(phoneNumber : String) : Response<Boolean>
    suspend fun checkDuplicatedId(id:String): Response<Boolean>

    suspend fun checkDuplicateNickname(nickname:String) :Response<Boolean> //닉네임 중복 체크

    suspend fun saveUserData(userInfo: UserRequestDTO) :Response<Boolean> //회원가입 데이터 저장



}