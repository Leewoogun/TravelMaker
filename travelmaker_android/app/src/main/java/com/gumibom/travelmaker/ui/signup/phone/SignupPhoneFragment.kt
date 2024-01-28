package com.gumibom.travelmaker.ui.signup.phone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.gumibom.travelmaker.databinding.FragmentSignupPhoneBinding
import com.gumibom.travelmaker.ui.signup.SignupActivity
import com.gumibom.travelmaker.ui.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupPhoneFragment : Fragment() {

    private var _binding : FragmentSignupPhoneBinding? = null
    private val binding get() = _binding!!
    private val signupViewModel : SignupViewModel by activityViewModels()

    private lateinit var phoneEditText : TextInputEditText
    private lateinit var certificationEditText : TextInputEditText
    private lateinit var activity:SignupActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as SignupActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneEditText = binding.tieSignupPhone
        certificationEditText = binding.tieSignupCertificationNumber

        getCertificationNumber()
        backAndNextNaviBtn()
    }

    private fun getCertificationNumber() {
        binding.btnSignupCertificationPhone.setOnClickListener {
            val phoneNumber = binding.btnSignupCertificationPhone.text.toString()
            signupViewModel.sendPhoneNumber(phoneNumber)

            setTimer()
        }
    }

    // 인증번호 요청 시 사용자에게 3분 타이머를 보여주는 함수
    private fun setTimer() {

    }

    // 뒤로 가기 버튼 클릭 시 EditText의 focus를 없애는 콜백 함수
    val callback = object : OnBackPressedCallback(true ) {
        override fun handleOnBackPressed() {
            if (phoneEditText.isFocused) {
                clearFocusAndHideKeyboard(phoneEditText)
            } else if (certificationEditText.isFocused) {
                clearFocusAndHideKeyboard(certificationEditText)
            } else {
                isEnabled = false
                // TODO 네비게이션 뒤로가기로 변경 필요
                activity?.onBackPressed()
            }
        }
    }



    private fun clearFocusAndHideKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
    private fun backAndNextNaviBtn(){
        binding.tvSignupPhonePrevious.setOnClickListener {
            activity.navigateToPreviousFragment()
        }
        binding.tvSignupPhoneNext.setOnClickListener {
            activity.navigateToNextFragment()
        }
    }
}