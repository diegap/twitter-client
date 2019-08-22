package katas.client.twitter.signup.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import katas.client.twitter.signup.domain.actions.RegisterUser
import timber.log.Timber

class SignupViewModel(private val registerUser: RegisterUser) : ViewModel() {

    private var disposable: Disposable? = null
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val navigation: MutableLiveData<String> = MutableLiveData()

    fun signup(userName: String, nickname: String) {
        disposable = registerUser.execute(userName, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
                errorMessage.value = "Error registering user $nickname"
            }, {
                Timber.d("Registered >> $userName@$nickname 0k")
                navigation.value = "tweets"
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}