package katas.client.twitter.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import katas.client.twitter.domain.actions.RegisterUser
import timber.log.Timber

class SignupViewModel(private val registerUser: RegisterUser) : ViewModel() {

    var disposable: Disposable? = null

    fun signup(userName: String, nickname: String) {
        disposable = registerUser.execute(userName, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
            }, {
                Timber.d("Registraton 0k")
                // TODO navigation pending
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}