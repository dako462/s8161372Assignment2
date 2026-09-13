package com.example.s8161372assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s8161372assignment2.adapter.EntityAdapter
import com.example.s8161372assignment2.databinding.FragmentDashboardBinding
import com.example.s8161372assignment2.viewmodel.DashboardViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var entityAdapter: EntityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView adapter
        entityAdapter = EntityAdapter { entity ->

            // Convert selected entity to JSON
            val entityJson = Gson().toJson(entity)

            // Send selected entity to DetailsFragment
            val bundle = Bundle().apply {
                putString("entityJson", entityJson)
            }

            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }

        binding.dashboardRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = entityAdapter
        }

        // Receive keypass from LoginFragment
        val keypass = arguments?.getString("keypass")

        // Observe dashboard API data
        viewModel.dashboardData.observe(viewLifecycleOwner) { response ->

            response?.let {

                entityAdapter.submitList(it.entities)

                binding.entityCountTextView.text =
                    "Total entities: ${it.entityTotal}"

                binding.errorTextView.visibility = View.GONE
            }
        }

        // Loading indicator
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->

            binding.progressBar.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        // Error handling
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->

            if (!message.isNullOrBlank()) {

                binding.errorTextView.text = message
                binding.errorTextView.visibility = View.VISIBLE

            } else {

                binding.errorTextView.visibility = View.GONE
            }
        }

        // Load dashboard
        if (!keypass.isNullOrBlank()) {

            viewModel.loadDashboard(keypass)

        } else {

            binding.errorTextView.text = "Keypass not received"
            binding.errorTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}