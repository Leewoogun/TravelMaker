package com.gumibom.travelmaker.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.data.dto.request.FcmTokenRequestDTO
import com.gumibom.travelmaker.databinding.ActivityMainBinding
import com.gumibom.travelmaker.model.User
import com.gumibom.travelmaker.ui.main.findmate.FindMateActivity
import com.gumibom.travelmaker.ui.signup.SignupViewModel
import com.gumibom.travelmaker.util.PermissionChecker
import com.gumibom.travelmaker.util.SharedPreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity_싸피"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    private val binding get() = _binding!!
    private lateinit var navController : NavController
    private val viewModel : MainViewModel by viewModels()
    private lateinit var user : User
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: @#!@#!@#")
        _binding = ActivityMainBinding.inflate(layoutInflater).apply {
            navController = (supportFragmentManager.findFragmentById(R.id.fragment_container_main)
                    as NavHostFragment).navController
        }
        setContentView(binding.root)
        sharedPreferencesUtil = SharedPreferencesUtil(this)

        /**
         * TODO Token을 가지고 서버에 User data를 전부받아서 저장
         */
        setFirebase()
        observeViewModel()
        setNavigationMenuToolbar()
        initToolbar()
    }
    private fun setNavigationMenuToolbar(){
        val mypage =
        //프래그먼트가 ~~ 일 땐 ~~로
        //프래그먼트가 ㅌㅌ 일 땐 ㅌㅌ 로
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    Log.d(TAG, "setNavigationMenuToolbar: 1")
                    binding.toolbar.menu.clear() // 기존 메뉴 제거
                    binding.toolbar.inflateMenu(R.menu.menu_main) // 새 메뉴 설정
                    binding.toolbar.title = "메인 페이지"
                    binding.toolbar.navigationIcon = null
                    Log.d(TAG, "setNavigationMenuToolbar: 2")
                }
                R.id.mainMyGroupFragment, R.id.mainMyPageFragment -> {
                    binding.toolbar.menu.clear() // 기존 메뉴 제거
                    binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_24)
                    binding.toolbar.title = getString(R.string.mypage_title)
                    binding.toolbar.setNavigationOnClickListener {
                        Log.d(TAG, "setNavigationMenuToolbar: ")
                        navController.navigateUp()
                    }
                    binding.toolbar.inflateMenu(R.menu.detail_menu_main) // 새 메뉴 설정
                }
            }
        }
    }

    private fun setFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }
            // token log 남기기
            Log.d(TAG, "token: ${task.result?:"task.result is null"}")
            if(task.result != null){
                viewModel.uploadToken(FcmTokenRequestDTO("wnddnjs823",task.result!!) )
            }
        })
        createNotificationChannel(CHANNEL_ID, "travelmaker")
    }
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
    fun navigationToGotoTravel() {
//        navController.navigate(R.id.action_mainFindMateFragment_to_mainFindMateDetailFragment)
        navController.navigate(R.id.action_mainFragment_to_mainGoTravelFragment)
    }
    fun navigationToGroupMSG(){
        navController.navigate(R.id.action_mainFragment_to_mainMyGroupFragment)
    }
    fun navigationToReadMyRecord(){
        navController.navigate(R.id.action_mainFragment_to_mainMyRecordFragment)
    }
    fun navigationToLookAroundPam(){
        navController.navigate(R.id.action_mainFragment_to_mainLookPamphletsFragment)
    }

    fun navigationPop() {
        navController.navigateUp()
    }

    fun observeViewModel(){
        viewModel.isUploadToken.observe(this){
            if (it.isSuccess){
                Log.d(TAG, "서버통신 성공 : ${it.isSuccess}")
            }else{
                Log.d(TAG, "실패 데스 : ${it.isSuccess}")
            }
        }
    }
    fun moveGoogleMap() {
        val intent = Intent(this, FindMateActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }
    private fun initToolbar(){

        binding.toolbar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        val imageView = findViewById<ImageView>(R.id.my_custom_icon)
        imageView.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_mainMyPageFragment)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_notify -> {
                    Log.d(TAG, "initToolbar: Noyigiyu")

                    true
                }
                R.id.action_search -> {
                    Log.d(TAG, "initToolbar:SEARYCDGC")
                    true
                }
                else -> false
            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null //메모리 누수 방지
    }

    companion object {
        // Notification Channel ID
        const val CHANNEL_ID = "travelmaker_channel" //mainActivity의 채널

    }

}

