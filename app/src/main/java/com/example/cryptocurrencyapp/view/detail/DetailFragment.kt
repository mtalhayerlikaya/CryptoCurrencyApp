package com.example.cryptocurrencyapp.view.detail

import android.os.Bundle
import android.util.Log
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
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var cryptoId: String
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
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(binding.fragmentDetailRoot).popBackStack()
            }
        })
    }

    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            detailViewModel.cryptoDetail.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarDetail.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarDetail.visibility = View.GONE
                        Log.e("detail", result.result.toString())
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
                        binding.detailCurrentPrice.text = result.result.toString()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




