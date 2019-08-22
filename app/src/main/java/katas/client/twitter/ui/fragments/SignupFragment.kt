package katas.client.twitter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import katas.client.twitter.R
import katas.client.twitter.ui.koinProxy
import katas.client.twitter.ui.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_signup.*
import timber.log.Timber

object SignupViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        koinProxy.get<SignupViewModel>() as T
}

class SignupFragment : Fragment() {
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupViewModel =
            ViewModelProvider(this, SignupViewModelFactory).get(
                SignupViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupButton.setOnClickListener {
            signupViewModel.signup(
                userNameEditText.text.toString(),
                nicknameEditText.text.toString()
            )
        }
        signupViewModel.navigation.observe(viewLifecycleOwner, Observer {
            when (it) {
                "tweets" -> {
                    findNavController().navigate(R.id.action_signupFragment_to_tweetsFragment)
                }
                else -> {
                    Timber.d("Unknown navigation")
                }
            }
        })
    }
}