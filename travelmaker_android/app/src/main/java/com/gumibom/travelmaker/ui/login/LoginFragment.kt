package com.gumibom.travelmaker.ui.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gumibom.travelmaker.MainActivity
import com.gumibom.travelmaker.constant.NO_LOGIN
import com.gumibom.travelmaker.constant.SUCCESS_LOGIN
import com.gumibom.travelmaker.data.dto.request.LoginRequestDTO
import com.gumibom.travelmaker.databinding.FragmentLoginBinding
import com.gumibom.travelmaker.databinding.FragmentLoginFindIdBinding
import com.gumibom.travelmaker.databinding.FragmentLoginFindPwBinding
import com.gumibom.travelmaker.model.User
import com.gumibom.travelmaker.ui.signup.SignupActivity
import com.gumibom.travelmaker.util.ApplicationClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment  : Fragment(){

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var activity: LoginActivity

    private val loginViewModel : LoginViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as LoginActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveFindIdPwFragment()
        login()
        observeLoginData()
    }

    // 아이디 찾기 클릭 시 아이디 찾기 화면으로 전환
    private fun moveFindIdPwFragment() {
        // bundle로 id 찾기 눌렀는지 비번 재설정 눌렀는지 확인
        binding.tvLoginFindId.setOnClickListener {
            val bundle = bundleOf("idOrPassword" to "0")
            activity.navigateToNextFragment(bundle)
        }

        binding.tvLoginFindPassword.setOnClickListener {
            val bundle = bundleOf("idOrPassword" to "1")
            activity.navigateToNextFragment(bundle)
        }
    }

    // 아이디 패스워드를 입력하고 로그인을 하는 함수
    private fun login() {
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.tieLoginId.text.toString()
            val password = binding.tieLoginPassword.text.toString()

            val loginRequestDTO = LoginRequestDTO(id, password)  // 서버에게 보낼 DTO
            val user = User(id, password) // 앱에 저장할 데이터

            // 아이디나 비밀번호가 비어있으면 다시 입력
            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), NO_LOGIN, Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(loginRequestDTO)
                ApplicationClass.sharedPreferencesUtil.addUser(user)
            }
        }
    }

    // LiveData를 관찰하여 로그인 성공 실패 여부를 확인하는 함수
    private fun observeLoginData() {
        loginViewModel.isLogin.observe(viewLifecycleOwner){ response->
            // 성공시 메인 화면 이동
            if (response.isSuccess) {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                activity.moveMainActivity()
            }
            // 실패시 저장했던 앱 유저 데이터 삭제
            else {
                Toast.makeText(requireContext(), NO_LOGIN, Toast.LENGTH_SHORT).show()
                ApplicationClass.sharedPreferencesUtil.deleteUser()
            }
        }
    }


}