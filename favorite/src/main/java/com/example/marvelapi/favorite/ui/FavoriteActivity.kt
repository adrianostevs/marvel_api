package com.example.marvelapi.favorite.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapi.base.BaseActivity
import com.example.marvelapi.di.FavoriteModule
import com.example.marvelapi.favorite.DaggerFavoriteComponent
import com.example.marvelapi.favorite.databinding.ActivityFavoriteBinding
import com.example.marvelapi.favorite.factory.ViewModelFactory
import com.example.marvelapi.ui.detail.DetailActivity
import com.example.marvelapi.ui.main.CharactersAdapter
import com.example.marvelapi.utils.ConstantsValue
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val mFavoriteViewModel by viewModels<FavoriteViewModel> {
        factory
    }

    private var mCharactersAdapter : CharactersAdapter? = null

    private var binding : ActivityFavoriteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        DaggerFavoriteComponent
            .builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModule::class.java
                )
            )
            .build()
            .inject(this)
        mCharactersAdapter = CharactersAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ConstantsValue.DATA, it)
            startActivity(intent)
        }
        binding?.apply {
            rvList.adapter = mCharactersAdapter
            rvList.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            mbBack.setOnClickListener {
                finish()
            }
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        mFavoriteViewModel.characterFavorite.observe(this) {
            if (it.isNotEmpty()) {
                binding?.rvList?.visibility = View.VISIBLE
                binding?.mbBack?.visibility = View.GONE
                mCharactersAdapter?.submitList(it)
            } else {
                binding?.rvList?.visibility = View.GONE
                binding?.mbBack?.visibility = View.VISIBLE
            }
        }
    }

}