package com.example.cryptocurrencyapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.databinding.HomeRecyclerviewItemBinding

class CryptoAdapter(val context: Context, private val cryptoList: List<CryptoModel>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {
    inner class CryptoViewHolder(var binding: HomeRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptoModel: CryptoModel) {
            binding.homeRecyclerviewCryptoName.text = cryptoModel.name
            binding.homeRecyclerviewCryptoSymbol.text = cryptoModel.symbol.uppercase()
            binding.homeRecyclerviewCrypto24hrPercentage.text = context.resources
                .getString(R.string.crypto_price_with_percentage,cryptoModel.price_change_percentage_24h,"%")
            binding.homeRecyclerviewCryptoCurrentPrice.text = cryptoModel.current_price
            if(!cryptoModel.price_change_percentage_24h.startsWith("-")){
                binding.homeRecyclerviewCrypto24hrPercentage
                    .setTextColor(context.resources.getColor(R.color.edittext_gain_percentage_color,context.theme))
            }else{
                binding.homeRecyclerviewCrypto24hrPercentage
                    .setTextColor(context.resources.getColor(R.color.edittext_lost_percentage_color,context.theme))
            }
            Glide.with(context)
                .load(cryptoModel.image)
                //.placeholder(R.drawable.piwo_48)
                .into(binding.homeRecyclerviewCryptoImage);
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