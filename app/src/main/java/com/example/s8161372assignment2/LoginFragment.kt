package com.example.s8161372assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s8161372assignment2.databinding.FragmentLoginBinding
import com.example.s8161372assignment2.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {

            val username =
                binding.usernameEditText.text.toString().trim()

            val password =
                binding.passwordEditText.text.toString().trim()

            binding.errorTextView.isVisible = false

            viewModel.login(
                username = username,
                password = password
            )
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->

            if (result != null) {

                val bundle = Bundle().apply {
                    putString("keypass", result.keypass)
                }

                findNavController().navigate(
                    R.id.action_loginFragment_to_dashboardFragment,
                    bundle
                )
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->

            if (!message.isNullOrBlank()) {
                binding.errorTextView.text = message
                binding.errorTextView.isVisible = true
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->

            binding.progressBar.isVisible = isLoading
            binding.loginButton.isEnabled = !isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}