package com.example.marvelapi.core.domain.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class Characters(
    val id: String?,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail,
    val isFavorite: Boolean
): Parcelable {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Characters>() {
            override fun areItemsTheSame(
                oldItem: Characters,
                newItem: Characters
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Characters,
                newItem: Characters
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
