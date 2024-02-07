package com.gumibom.travelmaker.data.api.firebase

import com.gumibom.travelmaker.data.dto.request.FcmGetNotifyListDTO
import com.gumibom.travelmaker.data.dto.request.FcmRequestGroupDTO
import com.gumibom.travelmaker.data.dto.request.FcmTokenRequestDTO
import com.gumibom.travelmaker.data.dto.response.IsSuccessResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FirebaseTokenService {

    //Token정보를 서버로 전송한다.
    @PUT("user/renewal-fcm-token")
    suspend fun uploadToken(
        @Body fcmTokenRequestDTO: FcmTokenRequestDTO
    ):Response<IsSuccessResponseDTO>

    //모임 요청
    @POST("meeting-post/request")
    suspend fun groupRequest(
        @Body fcmRequestGroup: FcmRequestGroupDTO
    ) : Response<IsSuccessResponseDTO>

    @POST("meeting-post/accept")
    suspend fun acceptCrew(
        @Body fcmRequestGroup: FcmRequestGroupDTO
    ) : Response<IsSuccessResponseDTO>
    @POST("meeting-post/refuse")
    suspend fun refuseCrew(
        @Body fcmRequestGroup: FcmRequestGroupDTO
    ) : Response<IsSuccessResponseDTO>

    @GET("meeting-post/all-request/")
    suspend fun getAllRequest(
        @Query("userId") userId : Long
    ) : Response<FcmGetNotifyListDTO>

//        @Query("userId") userId:Long,
//        @Query("fcmToken") fcmToken:String

}