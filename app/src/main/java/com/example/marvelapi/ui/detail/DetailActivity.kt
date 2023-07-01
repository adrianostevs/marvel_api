package com.example.marvelapi.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.marvelapi.R
import com.example.marvelapi.base.BaseActivity
import com.example.marvelapi.databinding.ActivityDetailBinding
import com.example.marvelapi.domain.model.Characters
import com.example.marvelapi.utils.ConstantsValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val mDetailViewModel: DetailViewModel by viewModels()

    override fun onViewBinding() = ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        val arguments = intent.getParcelableExtra<Characters>(ConstantsValue.DATA)
        mDetailViewModel.characters.value = arguments

        viewBinding.apply {
            mtvToolbar.text = arguments?.name
            mtvTitle.text = arguments?.name
            mtvDescription.text =
                if (arguments?.description?.isEmpty() == true) "No description yet" else arguments?.description
            val urlImage = "${arguments?.thumbnail?.path}.${arguments?.thumbnail?.extension}"
            Glide.with(aivImage)
                .load(urlImage)
                .into(aivImage)
            mbFavorite.setOnClickListener {
                lifecycleScope.launch {
                    mDetailViewModel.setFavorite().collect {
                        mDetailViewModel.characters.value = it
                    }
                }
            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        mDetailViewModel.characters.observe(this) {
            viewBinding.mbFavorite.text =
                if (!it.isFavorite) getString(R.string.add_to_favorites) else getString(
                    R.string.remove_from_favorites
                )
        }
    }
}