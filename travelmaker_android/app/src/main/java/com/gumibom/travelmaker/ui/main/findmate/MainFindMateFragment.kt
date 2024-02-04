package com.gumibom.travelmaker.ui.main.findmate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.databinding.ActivityMainBinding
import com.gumibom.travelmaker.databinding.FragmentMainFindMateBinding
import com.gumibom.travelmaker.model.Address
import com.gumibom.travelmaker.ui.main.MainActivity
import com.gumibom.travelmaker.ui.main.MainViewModel
import com.gumibom.travelmaker.ui.main.findmate.bottomsheet.MainFindMateDetailFragment
import com.gumibom.travelmaker.util.PermissionChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

private const val TAG = "MainFindMateFragment_싸피"
@AndroidEntryPoint
class MainFindMateFragment : Fragment(), Callback, GoogleMapReadyCallback {
    private var _binding:FragmentMainFindMateBinding? = null
    private val binding get() = _binding!!
    val bottomsheetFragment = MainFindMateDetailFragment()
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var activity : MainActivity
    private lateinit var googleMapWrapper : GoogleMapWrapper

    private val findMateViewModel : FindMateViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentMainFindMateBinding.inflate(inflater,container,false);
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        // Assuming the Bottom Sheet is part of the Fragment's layout
        val standardBottomSheet = binding.bts.bottomSheetLayout
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.state = STATE_HALF_EXPANDED;
        standardBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val screenHeight = Resources.getSystem().displayMetrics.heightPixels
                val halfHeight = screenHeight / 2
                val currentTop = screenHeight - bottomSheet.top
                val bottomToHalfSize = halfHeight+0/2;
                val halfToTop = (halfHeight+screenHeight)/2;
                when (currentTop) {
                    in 0 until bottomToHalfSize -> standardBottomSheetBehavior.state = STATE_COLLAPSED
                    in bottomToHalfSize until halfToTop -> standardBottomSheetBehavior.state = STATE_HALF_EXPANDED
                    in halfToTop  until  screenHeight -> standardBottomSheetBehavior.state = STATE_EXPANDED
                }
                Log.d(TAG, "onSlide: _ ${screenHeight}-${halfHeight}:${currentTop}-${bottomToHalfSize}-${halfToTop} : PKEEKEKEKEK")
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })

        googleMapWrapper = GoogleMapWrapper(requireContext())
        googleMapWrapper.setCallback(this)
        googleMapWrapper.setReadyCallback(this)
        binding.googleMap.onCreate(savedInstanceState)
        binding.googleMap.addView(googleMapWrapper)

        Log.d(TAG, "onViewCreated: $googleMapWrapper")
        observeLivaData()
        searchPlaces()

    }

    // 장소 검색으로 위치를 변화 시켰다면
    private fun checkChangeLocation() {
        Log.d(TAG, "checkChangeLocation: ${mainViewModel.address}")
        if (mainViewModel.address != null) {
            mainViewModel.setSelectAddress(mainViewModel.address!!)
        }
    }

    // 장소 검색 화면으로 넘어가기
    private fun searchPlaces() {
        binding.btnFindMatePlace.setOnClickListener {
            mainViewModel.address = null
            Navigation.findNavController(it).navigate(R.id.action_mainFindMateFragment_to_findMateSearchFragment)
        }
    }

    private fun observeLivaData() {

        // 현재 위치의 변화가 있으면 마커 리스트를 받아서 마커 표시
        findMateViewModel.markerList.observe(viewLifecycleOwner) { markerPosition ->
            // 내 위치 근방에 모임이 없다면
            if (markerPosition.isEmpty()) {
                val latitude = findMateViewModel.currentLatitude
                val longitude = findMateViewModel.currentLongitude

                val location = LatLng(latitude, longitude)
                googleMapWrapper.setMyLocation(location)
            }  // 있다면
            else {
                for (marker in markerPosition) {
                    val location = LatLng(marker.position.latitude, marker.position.longitude)
                    googleMapWrapper.setMarker(location, "")
                }
            }
            Log.d(TAG, "observeLivaData: $markerPosition")
        }
        mainViewModel.selectAddress.observe(viewLifecycleOwner) { address ->
            // TODO 여기서 새롭게 받은 address로 서버한테 넘겨서 위치 재갱신 하기

            // 여기는 Test 용
            val location = LatLng(address.latitude, address.longitude)
            googleMapWrapper.setMyLocation(location)
            binding.btnFindMatePlace.text = address.title
        }
    }

    /**
     * 위치 업데이트 콜백 함수
     */
    override fun onLocationUpdated(latitude: Double, longitude: Double) {
        findMateViewModel.currentLatitude = latitude
        findMateViewModel.currentLongitude = longitude

        Log.d(TAG, "onLocationUpdated: 통신하나요?")
//        findMateViewModel.getMarkers(latitude, longitude, 3.0)
        // 테스트 용 내 현재 위치
        if (mainViewModel.address == null) {
            googleMapWrapper.setMyLocation(LatLng(latitude, longitude))
        }
    }

    /**
     * 구글 맵이 준비될 때 동작하는 콜백 함수
     */
    override fun onMapReady() {
        checkChangeLocation()
    }

    // Set initial peek height


    // Assuming you have a method to convert dp to pixels
    fun Int.dpToPixels(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    private fun modalBottomSheetEvent(){
//        val bottomSheetBackCallback = object : OnBackPressedCallback(/* enabled= */false) {
//            override fun handleOnBackStarted(backEvent: BackEventCompat) {
//                bottomSheetBehavior.startBackProgress(backEvent)
//            }
//
//            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
//                bottomSheetBehavior.updateBackProgress(backEvent)
//            }
//
//            override fun handleOnBackPressed() {
//                bottomSheetBehavior.handleBackInvoked()
//            }
//
//            override fun handleOnBackCancelled() {
//                bottomSheetBehavior.cancelBackProgress()
//            }
//        }
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        googleMapWrapper.onResume()
//        checkChangeLocation()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        googleMapWrapper.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        googleMapWrapper.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        googleMapWrapper.onDestroy()
        Log.d(TAG, "onDestroyView called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach called")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        googleMapWrapper.onLowMemory()
    }




}



/*
private fun persistentBottomSheetEvent() {
    bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when(newState) {
                BottomSheetBehavior.STATE_COLLAPSED-> {

                }
                BottomSheetBehavior.STATE_DRAGGING-> {

                }
                BottomSheetBehavior.STATE_EXPANDED-> {
                    activity.testMoveFragment()
                }
                BottomSheetBehavior.STATE_HIDDEN-> {

                }
                BottomSheetBehavior.STATE_SETTLING-> {

                }
            }
        }
    })
}
*/
