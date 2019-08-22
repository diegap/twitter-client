package katas.client.twitter.profile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import katas.client.twitter.R
import katas.client.twitter.koinProxy
import katas.client.twitter.profile.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

object HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        koinProxy.get<HomeViewModel>() as T
}

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory).get(
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

        followButton.setOnClickListener {
            homeViewModel.follow(
                nicknameEditText.text.toString(),
                followEditText.text.toString()
            )
        }

        newTweetButton.setOnClickListener {
            findNavController().navigate(R.id.action_tweetsFragment_to_tweetDialogFragment)
        }

        homeViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            userNameEditText.setText(user.realName)
            nicknameEditText.setText(user.nickname)
            user.follows.forEach {
                val chip = Chip(context)
                chip.text = it
                followsChipGroup.addView(chip)
            }
        })
    }
}