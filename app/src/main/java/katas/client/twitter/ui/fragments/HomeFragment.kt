package katas.client.twitter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.material.chip.Chip
import katas.client.twitter.R
import katas.client.twitter.domain.actions.ShowHome
import katas.client.twitter.infra.repositories.LocalUserRepository
import katas.client.twitter.infra.repositories.RestUserRepository
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint
import katas.client.twitter.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private var retrofit: Retrofit? = null
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        retrofit = Retrofit.Builder().baseUrl("http://10.10.62.127:8080/api/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper())).build()

        return HomeViewModel(
            ShowHome(
                restUserRepository = RestUserRepository(retrofit!!.create(UserEndpoint::class.java)),
                localUserRepository = LocalUserRepository(context.applicationContext)
            )
        ) as T
    }
}

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(requireActivity().application)).get(
                HomeViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            userNameEditText.setText(user.realName)
            nicknameEditText.setText(user.nickname)
            user.follows.forEach{
                val chip = Chip(context)
                chip.text = it
                followsChipGroup.addView(chip)
            }
        })
    }
}