package com.gumibom.travelmaker.domain.signup

import com.gumibom.travelmaker.data.dto.google.GoogleLocationDTO
import com.gumibom.travelmaker.data.repository.google.GoogleLocationRepository
import com.gumibom.travelmaker.model.Address
import javax.inject.Inject

class GetGoogleLocationUseCase @Inject constructor(
    private val googleLocationRepositoryImpl: GoogleLocationRepository
) {
    suspend fun findGoogleLocation(location : String, apiKey : String) : MutableList<Address> {
        // 구글 DTO를 GoogleAddress로 바꾸기

        val response = googleLocationRepositoryImpl.findGoogleLocationSearch(location, apiKey)
        var googleAddressList = mutableListOf<Address>()

        // 응답이 성공이면 DTO -> model로 변환
        if (response.isSuccessful) {
            val body = response.body()

            val googleAddress = convertAddressModel(body)
            googleAddressList.add(googleAddress)
        }

        return googleAddressList
    }

    private fun convertAddressModel(body : GoogleLocationDTO?) : Address {
        // 데이터가 null인 경우 ""로 초기화
        val formattedAddress = body?.results?.get(0)?.formatted_address ?: ""
        //
        val country = formattedAddress.split(" ").lastOrNull()

        val googleAddress = Address(
            country,
            formattedAddress
        )

        return googleAddress
    }
}