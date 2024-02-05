package com.gumibom.travelmaker.data.datasource.meeting

import com.gumibom.travelmaker.data.dto.request.MarkerCategoryPositionRequestDTO
import com.gumibom.travelmaker.data.dto.request.MarkerPositionRequestDTO
import com.gumibom.travelmaker.data.dto.response.MarkerPositionResponseDTO
import com.gumibom.travelmaker.data.dto.response.MeetingPostDTO
import retrofit2.Response

interface MeetingRemoteDataSource {
    suspend fun getMarkerPositions(markerPositionRequestDTO: MarkerPositionRequestDTO) : Response<MutableList<MarkerPositionResponseDTO>>
    suspend fun getMarkerCategoryPositions(markerCategoryPositionRequestDTO: MarkerCategoryPositionRequestDTO) : Response<MutableList<MarkerPositionResponseDTO>>

    suspend fun getPostDetail(id:Long) : Response<MeetingPostDTO>
}