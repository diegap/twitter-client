package katas.client.twitter.profile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.io
import katas.client.twitter.profile.domain.actions.FollowUser
import katas.client.twitter.profile.domain.actions.ShowHome
import katas.client.twitter.signup.domain.entities.User
import timber.log.Timber

class HomeViewModel(private val showHome: ShowHome, private val followUser: FollowUser) :
    ViewModel() {

    init {
        showHome()
    }

    private var disposable: Disposable? = null
    val user: MutableLiveData<User> = MutableLiveData()

    private fun showHome() {
        disposable = showHome.execute()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
            }, {
                user.value = it
            })
    }

    fun follow(nickname: String, follow: String) {
        disposable = followUser.execute(nickname, follow)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
            }, {
                Timber.d("Following >> $follow!")
                user.value = it
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}