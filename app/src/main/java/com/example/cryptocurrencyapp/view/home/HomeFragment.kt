package com.example.cryptocurrencyapp.view.home

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.example.cryptocurrencyapp.view.loginregister.LoginRegisterActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getCryptos()
        observeFlow()
        logoutDialog()
    }
    private fun logoutDialog() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Do you want to logout ?")
                .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, which ->
                    homeViewModel.logout()
                    startActivity(Intent(activity, LoginRegisterActivity::class.java))
                })
                .setCancelable(false)
                .setNegativeButton(R.string.no, null)
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.allCryptos.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarHome.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarHome.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarHome.visibility = View.GONE
                        val adapter = CryptoAdapter(requireContext(), result.result.toMutableList()){crypto->
                            val action = HomeFragmentDirections.actionHomeFragment2ToDetailFragment(crypto.cryptoId)
                            binding.root.findNavController().navigate(action)
                        }
                        binding.homeRV.adapter = adapter
                        binding.homeRV.layoutManager =
                            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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