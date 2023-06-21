package com.example.cryptocurrencyapp.view.loginregister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.Resource
import com.example.cryptocurrencyapp.databinding.FragmentLoginBinding
import com.example.cryptocurrencyapp.databinding.FragmentRegisterBinding
import com.example.cryptocurrencyapp.utils.SignUpResult
import com.example.cryptocurrencyapp.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpBtn.setOnClickListener {
            val name = binding.nameEditTextRegister.text.toString()
            val email = binding.emailEditTextRegister.text.toString()
            val password = binding.passwordEditTextRegister.text.toString()
            val result = authViewModel.canUserSingUp(email, password)
            if (result == SignUpResult.OK) {
                authViewModel.signupUser(name, email, password)
            } else {
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()

            }
        }


        onBackPressed()
        observeFlow()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(binding.signUpBtn).popBackStack()
            }
        })
    }

    private fun observeFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.signupFlow.collect() { state ->
                when (state) {
                    is Resource.Failure -> {
                        binding.progressBarRegister.visibility = View.GONE
                        Toast.makeText(requireContext(), state.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarRegister.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBarRegister.visibility = View.GONE
                        startActivity(Intent(activity, MainActivity::class.java))
                        requireActivity().finish()
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