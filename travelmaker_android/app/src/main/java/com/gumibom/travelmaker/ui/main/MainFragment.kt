package com.gumibom.travelmaker.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding :FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonClickListener()
    }
    private fun initButtonClickListener(){
        binding.btnLookForMate.setOnClickListener {
            //activity. 버튼 이동로직들이 fragment에 있는데, activity에서 버튼 이동 관리하는 게 맞는건가?
            activity.navigationToNextFragment()
        }
        binding.btnCreateMyPamphlet.setOnClickListener {

        }
        binding.btnLookAroundPamphlet.setOnClickListener {

        }
        binding.btnMyGroup.setOnClickListener {

        }
        binding.btnReadMyRecord.setOnClickListener {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

}