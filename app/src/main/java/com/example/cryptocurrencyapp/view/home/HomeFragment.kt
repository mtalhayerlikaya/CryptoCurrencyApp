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
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.databinding.FragmentHomeBinding
import com.example.cryptocurrencyapp.view.loginregister.LoginRegisterActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: CryptoAdapter
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
        searchCrypto()
        logoutButtonSetOnClickListener()
    }

    private fun searchCrypto() {
        binding.homeSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { query ->
                    homeViewModel.searchCrypto(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    homeViewModel.getCryptos()
                }
                return true
            }

        })

    }

    private fun showLogoutDialog(){
        AlertDialog.Builder(context)
            .setTitle("Logout")
            .setMessage("Do you want to logout ?")
            .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, which ->
                homeViewModel.logout()
                requireActivity().finish()
                startActivity(Intent(activity, LoginRegisterActivity::class.java))
            })
            .setCancelable(false)
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }
    private fun logoutDialog() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showLogoutDialog()
        }
    }
    private fun logoutButtonSetOnClickListener(){
        binding.signOutHome.setOnClickListener {
            showLogoutDialog()
        }
    }


    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.allCryptos.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarHome.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarHome.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarHome.visibility = View.GONE
                        adapter = CryptoAdapter(requireContext(), result.result.toMutableList()) { crypto ->
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.searchCryptoFlow.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarHome.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarHome.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarHome.visibility = View.GONE
                        adapter.updateList(result.result.toMutableList())
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