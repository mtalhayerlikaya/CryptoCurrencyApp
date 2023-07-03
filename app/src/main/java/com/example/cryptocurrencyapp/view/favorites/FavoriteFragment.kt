package com.example.cryptocurrencyapp.view.favorites

import android.app.AlertDialog
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.data.model.FavoriteModel
import com.example.cryptocurrencyapp.databinding.FragmentFavoriteBinding
import com.example.cryptocurrencyapp.view.loginregister.LoginRegisterActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutDialog()
        observeFlow()
        favoriteViewModel.getAllFavoriteCryptos()
    }

    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            favoriteViewModel.firebaseAllFavCryptosFlow.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        binding.progressBarFavorite.visibility = View.GONE
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarFavorite.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarFavorite.visibility = View.GONE
                        setAdapter(result.result.toMutableList())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            favoriteViewModel.deleteCryptoFlow.collect() { result ->
                when (result) {
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), result.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Snackbar
                            .make(binding.favoriteRV, "Item was removed from the list", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setAdapter(list: MutableList<FavoriteModel>) {
        if(list.isNullOrEmpty()) binding.favoriteEmtyLayout.visibility = View.VISIBLE
        else binding.favoriteEmtyLayout.visibility = View.GONE
        val adapter = FavoritesCryptoAdapter(requireContext(), list) {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.cryptoId)
            binding.root.findNavController().navigate(action)
        }
        binding.favoriteRV.adapter = adapter
        binding.favoriteRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                favoriteViewModel.deleteCryptoFromFavorites(adapter.getCrypto(position))
                adapter.removeItem(position)
                if(list.isNullOrEmpty()) binding.favoriteEmtyLayout.visibility = View.VISIBLE
                else binding.favoriteEmtyLayout.visibility = View.GONE
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.favoriteRV)
    }

    private fun logoutDialog() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Do you want to logout ?")
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    favoriteViewModel.logout()
                    startActivity(Intent(activity, LoginRegisterActivity::class.java))
                }
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}