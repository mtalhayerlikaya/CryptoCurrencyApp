package com.example.cryptocurrencyapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.databinding.HomeRecyclerviewItemBinding

class CryptoAdapter(val contect: Context, private val cryptoList: List<CryptoModel>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {
    inner class CryptoViewHolder(var binding: HomeRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptoModel: CryptoModel) {
            binding.homeRecyclerviewCryptoName.text = cryptoModel.name
            binding.homeRecyclerviewCryptoSymbol.text = cryptoModel.symbol
            binding.homeRecyclerviewCrypto24hrPercentage.text = cryptoModel.price_change_percentage_24h
            binding.homeRecyclerviewCryptoCurrentPrice.text = cryptoModel.current_price
            if(!cryptoModel.price_change_percentage_24h.startsWith("-")){
                binding.homeRecyclerviewCrypto24hrPercentage
                    .setTextColor(contect.resources.getColor(R.color.edittext_gain_percentage_color,contect.theme))
            }else{
                binding.homeRecyclerviewCrypto24hrPercentage
                    .setTextColor(contect.resources.getColor(R.color.edittext_lost_percentage_color,contect.theme))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = HomeRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val cryptoModel: CryptoModel = cryptoList[position]
        holder.bind(cryptoModel)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }



}