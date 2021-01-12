package com.evangelidis.myipinfo.view

import android.graphics.Paint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evangelidis.myipinfo.R
import com.evangelidis.myipinfo.databinding.ItemIpInfoBinding
import com.evangelidis.myipinfo.extensions.*
import com.evangelidis.myipinfo.models.InfoModel

class IpInfoAdapter(private val specifications: List<InfoModel>) : RecyclerView.Adapter<IpInfoAdapter.IpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IpViewHolder {
        return IpViewHolder(ItemIpInfoBinding.inflate(parent.layoutInflater(), parent, false))
    }

    override fun getItemCount(): Int = specifications.count()

    override fun onBindViewHolder(holder: IpViewHolder, position: Int) {
        holder.onBind(specifications[position], position, specifications.size)
    }

    inner class IpViewHolder(private val binding: ItemIpInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(specification: InfoModel, position: Int, totalSpecifications: Int) {
            with(binding) {
                title.apply {
                    text = specification.title
                    paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                }
                info.text = specification.info
                ipItem.setBackgroundColor(root.context.color((position.isEven()) then { R.color.light_grey } ?: R.color.white))

                if (position == totalSpecifications - 1) {
                    bottomDivider.show()
                }
            }
        }
    }
}
