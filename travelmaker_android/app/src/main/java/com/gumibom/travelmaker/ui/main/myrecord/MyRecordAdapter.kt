package com.gumibom.travelmaker.ui.main.myrecord

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gumibom.travelmaker.R
import com.gumibom.travelmaker.databinding.ItemMyRecordBinding
import com.gumibom.travelmaker.model.pamphlet.PamphletItem

private const val TAG = "MyRecordAdapter_싸피"
class MyRecordAdapter(private val context : Context) : ListAdapter<PamphletItem, MyRecordAdapter.MyRecordViewHolder>(MyRecordDiffUtil()) {

    inner class MyRecordViewHolder(private val binding : ItemMyRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : PamphletItem) {
            Log.d(TAG, "bind: 다시 만드니?")
            // 여행 중인 경우
            if (!item.isFinish) {
                setItem(item)
                Log.d(TAG, "bind: 여행중")
                binding.tvItemMyRecordCreateTime.visibility = View.VISIBLE
                binding.btnMyRecordComplte.visibility = View.VISIBLE
            }
            // 여행 완료 인 경우
            else {
                setItem(item)
                Log.d(TAG, "bind: 여행완료")
                binding.tvItemMyRecordCreateTime.visibility = View.VISIBLE
                binding.btnMyRecordComplte.visibility = View.GONE
            }
        }

        private fun setItem(item : PamphletItem){
            binding.tvMyRecordTitle.text = item.title
            // TODO 팜플렛 표지 넣기
            val imageUrl = item.repreImgUrl
            Glide.with(context)
                .load(imageUrl)
                .transform(CenterCrop(), RoundedCorners(30))
                .placeholder(R.drawable.background_pamphlet)
                .into(binding.ivMyRecordPamphlet)
            binding.tvItemMyRecordCreateTime.text = item.createTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecordViewHolder {
        val binding = ItemMyRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}