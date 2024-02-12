package com.gumibom.travelmaker.data.datasource.signup


import com.gumibom.travelmaker.data.dto.request.PhoneCertificationRequestDTO
import com.gumibom.travelmaker.data.dto.request.SignInUserDataRequestDTO
import com.gumibom.travelmaker.data.dto.response.IsSuccessResponseDTO
import com.gumibom.travelmaker.data.dto.response.SignInResponseDTO

import retrofit2.Response

interface SignupRemoteDataSource {
    suspend fun sendPhoneNumber(phoneNumber : String) : Response<IsSuccessResponseDTO>
    suspend fun checkDuplicatedId(id:String): Response<SignInResponseDTO>

    suspend fun checkDuplicateNickname(nickname:String):Response<SignInResponseDTO>
    suspend fun saveUserData(userInfo: SignInUserDataRequestDTO) :Response<IsSuccessResponseDTO> //회원가입 데이터 저장

    suspend fun isCertificationNumber(phoneCertificationRequestDTO : PhoneCertificationRequestDTO) : Response<Boolean>

}