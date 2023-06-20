package com.example.cryptocurrencyapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.data.model.CryptoDetailResponse
import com.example.cryptocurrencyapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var cryptoId: String
    private var cryptoDetailResponse: CryptoDetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: DetailFragmentArgs by navArgs()
        cryptoId = bundle.cryptoID
        detailViewModel.getCryptoDetail(cryptoId)
        onBackPressed()
        observeFlow()
        initSetOnClickListenersAndUI()
    }

    private fun initSetOnClickListenersAndUI() {
        val items = arrayOf("10 second", "15 second", "20 second", "25 second", "30 second")
        val itemsMsList = listOf<Long>(10000, 15000, 20000, 25000, 30000)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.item_time_interval_dropdown_menu, items)
        binding.autoCompleteTextView.setAdapter(adapter)
        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            detailViewModel.getCryptosCurrentPriceByID(itemsMsList[position], cryptoId)
        }
        binding.favoriteCardView.setOnClickListener {
            cryptoDetailResponse?.let {detailModel->
                detailViewModel.addCryptoToFavorities(detailModel)
            }
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(binding.backCardView).popBackStack()
            }
        })
    }

    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.cryptoDetail.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarDetail.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarDetail.visibility = View.GONE
                        setUI(result.result)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.detailCurrentPrice.collect() { result ->
                when (result) {
                    is Resource.Failure -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        binding.detailCryptoPrice.text = result.result.toString()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.firebaseFav.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(),result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(requireContext(),"Added Fav Succesfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setUI(response: CryptoDetailResponse) {
        cryptoDetailResponse = response
        binding.detailCryptoName.text = response.name
        binding.detailCryptoSymbol.text = response.symbol.uppercase()
        binding.detailCryptoPrice.text = resources.getString(R.string.crypto_price, response.market_data.currentPrice.usd.toString(), "$")
        binding.cryptoDetailPriceIncrease.text = resources.getString(R.string.crypto_price_increase, response.market_data.price_change_24h.toString(), "$")
        binding.cryptoDetailPricePercentageIncrease.text = resources.getString(R.string.crypto_price_and_percentage_increase, response.market_data.price_change_percentage_24h.toString(), "%")
        binding.detailCryptoHashingName.text =
            if (response.hashing_algorithm.isNullOrEmpty()) "Empty" else response.hashing_algorithm
        binding.detailCryptoDescription.text = response.description.en

        Glide.with(requireContext())
            .load(response.image.large)
            .into(binding.detailCryptoImage)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




