package com.gumibom.travelmaker.ui.main.findmate.bottomsheet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.databinding.ActivityMainBinding
import com.gumibom.travelmaker.databinding.FragmentMainFindMateDetailBinding
import com.gumibom.travelmaker.ui.main.MainActivity
import com.gumibom.travelmaker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFindMateDetailFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentMainFindMateDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var activity : MainActivity
    private val viewModel : MainViewModel by activityViewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainFindMateDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setBottomSheet(){
//        val postDetail =
//        val imageUrls = getImageUrls(postDetail)
//        val adapter = ImageAdapter(imageUrls)
//        recyclerView.adapter = adapter //

        //chhip
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
