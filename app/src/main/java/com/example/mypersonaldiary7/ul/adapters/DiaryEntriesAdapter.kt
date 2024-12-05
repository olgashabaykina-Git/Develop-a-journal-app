package com.example.mypersonaldiary7.ul.adapters


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypersonaldiary7.R
import com.example.mypersonaldiary7.data.model.DiaryEntries
import java.text.SimpleDateFormat
import java.util.Locale
//The adapter for the Recycler View is used to display a list of entries in the diary
class DiaryEntriesAdapter(
    private val onItemClicked: (DiaryEntries) -> Unit
) : RecyclerView.Adapter<DiaryEntriesAdapter.DiaryViewHolder>() {
    // The list of records to be displayed
    private var diaryEntriesList = mutableListOf<DiaryEntries>()
    // Method for updating the list of data in the adapter
    fun submitList(entries: List<DiaryEntries>) {
        diaryEntriesList.clear()
        diaryEntriesList.addAll(entries)
        notifyDataSetChanged()
    }
    // Creates a new ViewHolder that will be used to display the list item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary_entry, parent, false)
        return DiaryViewHolder(view)
    }
    // Binds data from the list to a specific ViewHolder
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val entry = diaryEntriesList[position]
        holder.bind(entry)
        holder.itemView.setOnClickListener {
            onItemClicked(entry)
        }
    }
    // Returns the number of items in the list
    override fun getItemCount(): Int = diaryEntriesList.size
    // ViewHolder — a class for controlling the display of a single list item
    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val entryImageView: ImageView = itemView.findViewById(R.id.entryImageView)
        // Binds record data to interface elements
        fun bind(entry: DiaryEntries) {
            titleTextView.text = entry.title
            val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.getDefault())
            dateTextView.text = formatter.format(entry.createdAt)

            if (!entry.imageUri.isNullOrEmpty()) {
                // If there is an image, show it
                entryImageView.visibility = View.VISIBLE
                try {
                    val uri = Uri.parse(entry.imageUri)
                    entryImageView.setImageURI(uri)
                } catch (e: Exception) {
                    entryImageView.visibility = View.GONE
                }
            } else {
                // If there is no image, hide the ImageView
                entryImageView.visibility = View.GONE
            }
        }

    }
}


