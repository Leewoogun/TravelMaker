package com.gumibom.travelmaker.ui.signup.location

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.databinding.ItemLocationListBinding
import com.gumibom.travelmaker.model.Address
import com.gumibom.travelmaker.model.KakaoAddress
import com.gumibom.travelmaker.model.NaverAddress
import com.gumibom.travelmaker.ui.signup.SignupViewModel


private const val TAG = "SignupLocationAdapter_싸피"
class SignupLocationAdapter(private val context : Context, private val viewModel : SignupViewModel) : ListAdapter<Address, SignupLocationAdapter.SignupLocationViewHolder>(SignupLocationDiffUtil()) {

    // 클릭한 아이템의 position을 갱신하기 위한 변수
    private var selectItemPosition = -1
    private var previousItem = -1

    inner class SignupLocationViewHolder(private val binding : ItemLocationListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Address, position : Int) {
            binding.tvItemLocationTitle.text = item.title!!
            binding.tvItemLocationAddress.text = item.address

            // 아이템을 선택했을 때 color 변경을 위한 변수
            val notSelectColor = ContextCompat.getColor(context, R.color.white)
            val selectColor = ContextCompat.getColor(context, R.color.gray_a25)

            binding.itemLocationLayout.setOnClickListener {

                previousItem = selectItemPosition

                // 클릭한 아이템이 이전에 선택한 아이템과 다르면 선택된 아이템으로 설정하고 배경색 변경
                if (position != selectItemPosition) {
                    selectItemPosition = position

                    // 변경된 아이템을 업데이트합니다.
                    notifyItemChanged(previousItem)
                    notifyItemChanged(position)

                    // 선택한 아이템의 주소를 ViewModel에 저장
                    viewModel.setAddress(item.address)
                } else {
                    // 이미 선택된 아이템을 다시 클릭하면 이전에 선택한 아이템과 현재 선택한 아이템을 초기화하고 배경색 변경
                    selectItemPosition = -1
                    notifyItemChanged(previousItem)
                    notifyItemChanged(position)

                    // 선택한 아이템의 주소를 ViewModel에서 초기화
                    viewModel.setAddress("")
                }

                Log.d(TAG, "bind position: $position")
                Log.d(TAG, "bind selectPosition: $selectItemPosition")
                Log.d(TAG, "bind: ${viewModel.address}")
            }

            // 배경색을 설정합니다. 선택된 아이템이면 다른 색으로 설정합니다.
            if (position == selectItemPosition) {
                binding.itemLocationLayout.setBackgroundColor(selectColor)
            } else {
                binding.itemLocationLayout.setBackgroundColor(notSelectColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignupLocationViewHolder {
        val binding = ItemLocationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SignupLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SignupLocationViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}