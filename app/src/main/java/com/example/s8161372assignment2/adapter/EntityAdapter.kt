package com.example.s8161372assignment2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s8161372assignment2.databinding.ItemEntityBinding

class EntityAdapter(
    private var entities: List<Map<String, Any?>> = emptyList(),
    private val onItemClick: (Map<String, Any?>) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Map<String, Any?>) {

            val title = entity["name"]
                ?: entity["title"]
                ?: entity["property1"]
                ?: entity["id"]
                ?: "Entity"

            binding.entityTitle.text = title.toString()

            val details = entity.entries
                .filter {
                    it.key != "name" &&
                            it.key != "title" &&
                            it.key != "description"
                }
                .joinToString("\n") { entry ->
                    "${entry.key}: ${entry.value ?: ""}"
                }

            binding.entityDetails.text =
                if (details.isBlank()) {
                    "No summary available"
                } else {
                    details
                }

            binding.root.setOnClickListener {
                onItemClick(entity)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityViewHolder {

        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EntityViewHolder,
        position: Int
    ) {
        holder.bind(entities[position])
    }

    override fun getItemCount(): Int {
        return entities.size
    }

    fun submitList(newEntities: List<Map<String, Any?>>) {
        entities = newEntities
        notifyDataSetChanged()
    }
}