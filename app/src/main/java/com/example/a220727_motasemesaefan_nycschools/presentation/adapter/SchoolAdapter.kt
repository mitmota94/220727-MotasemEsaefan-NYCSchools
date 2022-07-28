package com.example.a220727_motasemesaefan_nycschools.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a220727_motasemesaefan_nycschools.data.model.SchoolItem
import com.example.a220727_motasemesaefan_nycschools.databinding.SchoolListItemBinding


class SchoolAdapter(
    private val schoolList: MutableList<SchoolItem> = mutableListOf(),
    private val setSchool: (SchoolItem) -> Unit
) : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    fun setSchoolsList(newList: List<SchoolItem>) {
        schoolList.clear()
        schoolList.addAll(newList)
        notifyItemRangeChanged(0, itemCount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder =
        SchoolViewHolder(
            SchoolListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(schoolList[position])
    }

    override fun getItemCount(): Int = schoolList.size


    inner class SchoolViewHolder(
        private val binding: SchoolListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(schoolItem: SchoolItem) {
            binding.apply {
                tvSchoolName.text = schoolItem.schoolName
                tvPhoneNumber.text = schoolItem.phoneNumber
                tvLocation.text = schoolItem.primaryAddressLine1
                btnAddInfo.setOnClickListener {
                    setSchool(schoolItem)
                }
            }
        }
    }




}