package com.example.cryptocurrencyapp.view.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentFavoriteBinding
import com.example.cryptocurrencyapp.databinding.FragmentProfileBinding
import com.example.cryptocurrencyapp.view.favorites.FavoriteViewModel
import com.example.cryptocurrencyapp.view.loginregister.LoginRegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutDialog()
    }

    private fun logoutDialog() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Do you want to logout ?")
                .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, which ->
                    detailViewModel.logout()
                    startActivity(Intent(activity, LoginRegisterActivity::class.java))
                })
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