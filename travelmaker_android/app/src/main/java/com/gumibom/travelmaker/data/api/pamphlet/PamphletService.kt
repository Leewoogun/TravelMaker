package com.gumibom.travelmaker.data.api.pamphlet

import com.gumibom.travelmaker.data.dto.response.PamphletResponseDTO
import com.gumibom.travelmaker.model.pamphlet.PamphletItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PamphletService {
    @Multipart
    @POST("/personal-pamphlet")
    suspend fun makePamphlet(
        @Part image : MultipartBody.Part,
        @Part("makePPReqDto") pamphletRequestDTO : RequestBody
    ) : Response<PamphletResponseDTO>

    @GET("/personal-pamphlet/v2/{userId}")
    suspend fun getMyRecord(@Path("userId") userId : Long) : Response<List<PamphletItem>>
}
