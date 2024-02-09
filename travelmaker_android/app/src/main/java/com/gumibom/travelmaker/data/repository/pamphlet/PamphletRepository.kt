package com.gumibom.travelmaker.data.repository.pamphlet

import com.gumibom.travelmaker.data.dto.response.PamphletResponseDTO
import com.gumibom.travelmaker.model.pamphlet.PamphletItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface PamphletRepository {
    suspend fun makePamphlet(image : MultipartBody.Part, pamphletRequestDTO: RequestBody) : Response<PamphletResponseDTO>
    suspend fun getMyRecord(userId: Long) : Response<List<PamphletItem>>
}