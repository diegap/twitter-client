package katas.client.twitter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import katas.client.twitter.R
import katas.client.twitter.domain.actions.RegisterUser
import katas.client.twitter.infra.repositories.RestUserRepository
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint
import katas.client.twitter.ui.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_signup.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

object SignupViewModelFactory : ViewModelProvider.Factory {

    var retrofit : Retrofit? = null
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        retrofit = Retrofit.Builder().baseUrl("http://10.10.62.127:8080/api/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create()).build()
        return SignupViewModel(RegisterUser(RestUserRepository(retrofit!!.create(UserEndpoint::class.java)))) as T
    }
}

class SignupFragment : Fragment() {

    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupViewModel = ViewModelProvider(this, SignupViewModelFactory).get(SignupViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupButton.setOnClickListener {
            signupViewModel.signup(userNameEditText.text.toString(), nicknameEditText.text.toString())
        }
    }
}