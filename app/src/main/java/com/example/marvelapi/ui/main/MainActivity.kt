package com.example.marvelapi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapi.BuildConfig
import com.example.marvelapi.base.BaseActivity
import com.example.marvelapi.databinding.ActivityMainBinding
import com.example.marvelapi.ui.detail.DetailActivity
import com.example.marvelapi.utils.ConstantsValue
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mCharactersAdapter: CharactersAdapter? = null

    private val mMainViewModel: MainViewModel by viewModels()

    override fun onViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        mCharactersAdapter = CharactersAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ConstantsValue.DATA, it)
            startActivity(intent)
        }
        viewBinding.apply {
            rvList.adapter = mCharactersAdapter
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            fabFavorite.setOnClickListener {
                installRegistrationModule()
            }
        }
        mMainViewModel.getAllCharacters(System.currentTimeMillis().toString())
        observeLiveData()
    }

    private fun observeLiveData() {
        mMainViewModel.charactersList.observe(this) {
            when (it) {
                is com.example.marvelapi.core.data.AppResult.Success -> {
                    mCharactersAdapter?.submitList(it.data)
                }
                is com.example.marvelapi.core.data.AppResult.Loading -> {

                }
                is com.example.marvelapi.core.data.AppResult.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun installRegistrationModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        if (splitInstallManager.installedModules.contains("favorite")) {
            val i = Intent()
            i.setClassName(
                BuildConfig.APPLICATION_ID,
                "com.example.marvelapi.favorite.ui.FavoriteActivity"
            )
            startActivity(i)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule("favorite")
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    installRegistrationModule()
                }
                .addOnFailureListener {
                }
        }
    }
}