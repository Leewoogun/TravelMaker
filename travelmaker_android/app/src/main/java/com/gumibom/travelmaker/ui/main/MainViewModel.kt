package com.gumibom.travelmaker.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.gumibom.travelmaker.constant.GOOGLE_API_KEY
import com.gumibom.travelmaker.constant.KAKAO_API_KEY

import com.gumibom.travelmaker.data.dto.request.MarkerCategoryPositionRequestDTO
import com.gumibom.travelmaker.data.dto.request.MarkerPositionRequestDTO
import com.gumibom.travelmaker.data.dto.request.MeetingPostRequestDTO
import com.gumibom.travelmaker.domain.meeting.GetMarkerCategoryPositionsUseCase
import com.gumibom.travelmaker.data.dto.request.FcmRequestGroupDTO
import com.gumibom.travelmaker.data.dto.request.FcmTokenRequestDTO
import com.gumibom.travelmaker.data.dto.response.IsSuccessResponseDTO

import com.gumibom.travelmaker.domain.firebase.FirebaseAcceptCrewUseCase
import com.gumibom.travelmaker.domain.firebase.FirebaseFcmUploadTokenUseCase
import com.gumibom.travelmaker.domain.firebase.FirebaseRequestGroupUseCase

import com.gumibom.travelmaker.domain.meeting.GetMarkerPositionsUseCase
import com.gumibom.travelmaker.domain.meeting.GetPostDetailUseCase
import com.gumibom.travelmaker.domain.signup.GetGoogleLocationUseCase
import com.gumibom.travelmaker.domain.signup.GetKakaoLocationUseCase
import com.gumibom.travelmaker.model.Address
import com.gumibom.travelmaker.model.BooleanResponse
import com.gumibom.travelmaker.model.MarkerPosition
import com.gumibom.travelmaker.model.PostDetail
import com.gumibom.travelmaker.ui.common.CommonViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel_싸피"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGoogleLocationUseCase: GetGoogleLocationUseCase,
    private val getKakaoLocationUseCase: GetKakaoLocationUseCase,
    private val getMarkerPositionsUseCase : GetMarkerPositionsUseCase,
    private val getMarkerCategoryPositionsUseCase: GetMarkerCategoryPositionsUseCase,
    private val requestGroupUseCase: FirebaseRequestGroupUseCase,
    private val acceptCrewUseCase: FirebaseAcceptCrewUseCase,
    private val getPostDetailUseCase:GetPostDetailUseCase,
    private val firebaseFcmUploadTokenUseCase: FirebaseFcmUploadTokenUseCase

) : ViewModel(), CommonViewModel {



    private val _isRequestSuccess = MutableLiveData<BooleanResponse>()
    val isRequestSuccess :LiveData<BooleanResponse> = _isRequestSuccess
    fun requestGroup(firebaseDTO: FcmRequestGroupDTO){
        viewModelScope.launch {
            _isRequestSuccess.value = requestGroupUseCase.requestGroup(firebaseDTO)
        }
    }

    var address : Address? = null

    // 현재 위치의 위도 경도
    var currentLatitude = 0.0
    var currentLongitude = 0.0

    // 초기 찐 내 위치의 위도 경도
    var initLatitude = 0.0
    var initLongitude = 0.0

    private val _markerList = MutableLiveData<List<MarkerPosition>>()
    val markerList : LiveData<List<MarkerPosition>> = _markerList

    private val _markerCategoryList = MutableLiveData<List<MarkerPosition>>()
    val markerCategoryList : LiveData<List<MarkerPosition>> = _markerCategoryList


    private val _kakaoAddressList = MutableLiveData<MutableList<Address>>()
    val kakaoAddressList : LiveData<MutableList<Address>> = _kakaoAddressList

    private val _googleAddressList = MutableLiveData<MutableList<Address>>()
    val googleAddressList : LiveData<MutableList<Address>> = _googleAddressList

    private val _selectAddress = MutableLiveData<Address>()
    val selectAddress : LiveData<Address> = _selectAddress

    //근방 위치를 통해 해당 정보의 데이터를 가져와서 viewmodel에 저장함.
    private val _postDTO = MutableLiveData<PostDetail>()
    val postDTO:LiveData<PostDetail> = _postDTO

    //서버에서 마커를 클릭한 정보들을 가져옴 -> ui단에서 받은 데이터들을 저장하장
    fun getPostDetail(pos:Long){
        viewModelScope.launch {
            _postDTO.value = getPostDetailUseCase.getPostDetail(pos)
        }
    }

    private val _isUploadToken = MutableLiveData<BooleanResponse>()
    val isUploadToken:LiveData<BooleanResponse> = _isUploadToken
    fun uploadToken(firebaseFcmTokenRequestDTO: FcmTokenRequestDTO){
        viewModelScope.launch {
            _isUploadToken.value = firebaseFcmUploadTokenUseCase.uploadToken(firebaseFcmTokenRequestDTO)
        }
    }

    // 서버에서 내 근방 위치 모임들을 가져옴
    fun getMarkers(markerPositionRequestDTO : MarkerPositionRequestDTO) {
        viewModelScope.launch {
            _markerList.value = getMarkerPositionsUseCase.getMarkerPositions(markerPositionRequestDTO)
        }
    }

    // 내 근방 위치 모임 카테고리 필터링
    fun getCategoryMarkers(markerCategoryPositionRequestDTO: MarkerCategoryPositionRequestDTO) {
        viewModelScope.launch {
            // TODO 마커 리스트로 고쳐야하나?
            _markerList.value = getMarkerCategoryPositionsUseCase.getMarkerCategoryPositions(markerCategoryPositionRequestDTO)
        }
    }

    fun getKakaoLatLng(location : String) {
        viewModelScope.launch {
            _kakaoAddressList.value = getKakaoLocationUseCase.getKakaoLocation(KAKAO_API_KEY, location)
        }
    }
    fun getGoogleLatLng(location : String) {
        viewModelScope.launch{
            _googleAddressList.value = getGoogleLocationUseCase.findGoogleLocation(location, GOOGLE_API_KEY)
        }
    }

    fun userSelectAddress(address : Address) {
        _selectAddress.value = address
        Log.d(TAG, "userSelectAddress: $address")
    }
    override fun setAddress(address: String) {

    }

    fun setSelectAddress(address : Address) {
        _selectAddress.value = address
    }

}