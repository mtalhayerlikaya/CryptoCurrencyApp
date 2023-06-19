package com.example.cryptocurrencyapp.view.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.data.model.CryptoModel
import com.example.cryptocurrencyapp.data.model.FavoriteModel
import com.example.cryptocurrencyapp.databinding.FavoritesRecyclerviewItemBinding

class FavoritesCryptoAdapter(
    val context: Context, private val cryptoList: MutableList<FavoriteModel>,
    private val cryptoRowListener: (cryptoModel: FavoriteModel) -> Unit
) : RecyclerView.Adapter<FavoritesCryptoAdapter.FavoritesCryptoVH>() {

    inner class FavoritesCryptoVH(var binding: FavoritesRecyclerviewItemBinding) : ViewHolder(binding.root) {
        fun bind(cryptoModel: FavoriteModel) {

            binding.favoritesRecyclerviewCryptoName.text = cryptoModel.name
            binding.favoritesRecyclerviewCryptoSymbol.text = cryptoModel.symbol.uppercase()
            binding.favoritesRecyclerviewCrypto24hrPercentage.text = context.resources
                .getString(R.string.crypto_price_with_percentage, cryptoModel.price_change_percentage_24h, "%")
            binding.favoritesRecyclerviewCryptoCurrentPrice.text = cryptoModel.current_price
            if (!cryptoModel.price_change_percentage_24h.startsWith("-")) {
                binding.favoritesRecyclerviewCrypto24hrPercentage
                    .setTextColor(context.resources.getColor(R.color.edittext_gain_percentage_color, context.theme))
            } else {
                binding.favoritesRecyclerviewCrypto24hrPercentage
                    .setTextColor(context.resources.getColor(R.color.edittext_lost_percentage_color, context.theme))
            }
            Glide.with(context)
                .load(cryptoModel.image)
                //.placeholder(R.drawable.piwo_48)
                .into(binding.favoritesRecyclerviewCryptoImage);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesCryptoVH {
        val binding = FavoritesRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesCryptoVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritesCryptoVH, position: Int) {
        val cryptoModel = cryptoList[position]
        holder.bind(cryptoModel)
        holder.binding.favoritesRecyclerviewItemRoot.setOnClickListener {
            cryptoRowListener.invoke(cryptoModel)
        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    fun removeItem(position: Int) {
        cryptoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getCrypto(position: Int): FavoriteModel {
        return cryptoList[position]
    }

}