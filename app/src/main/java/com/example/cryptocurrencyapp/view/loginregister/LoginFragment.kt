package com.example.cryptocurrencyapp.view.loginregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.databinding.FragmentLoginBinding
import com.example.cryptocurrencyapp.utils.LoginResult

import com.example.cryptocurrencyapp.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
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
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpLinearLayout.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val result = authViewModel.canUserLogin(email, password)
            if (result == LoginResult.OK) {
                authViewModel.loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
        }

        observeFlow()
    }

    private fun observeFlow() {

        authViewModel.currentUser?.let {
            startActivity(Intent(activity, MainActivity::class.java))
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.loginFlow.collect() { state ->
                when (state) {
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.exceptionMessage, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
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