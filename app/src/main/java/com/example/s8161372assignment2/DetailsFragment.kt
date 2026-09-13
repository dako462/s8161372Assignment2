package com.example.s8161372assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s8161372assignment2.databinding.FragmentDetailsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val entityJson = arguments?.getString("entityJson")

        if (!entityJson.isNullOrBlank()) {

            val type = object : TypeToken<Map<String, Any?>>() {}.type

            val entity: Map<String, Any?> =
                Gson().fromJson(entityJson, type)

            val title = entity["name"]
                ?: entity["title"]
                ?: entity["property1"]
                ?: entity["id"]
                ?: "Entity Details"

            binding.detailsTitleTextView.text = title.toString()

            val details = entity.entries
                .filter {
                    it.key != "name" &&
                            it.key != "title"
                }
                .joinToString("\n\n") { entry ->
                    "${entry.key}: ${entry.value ?: ""}"
                }

            binding.detailsContentTextView.text =
                if (details.isBlank()) {
                    "No additional details available."
                } else {
                    details
                }

        } else {

            binding.detailsTitleTextView.text = "Entity Details"
            binding.detailsContentTextView.text =
                "No entity data received."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}