package katas.client.twitter.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers.io
import katas.client.twitter.domain.actions.ShowHome
import katas.client.twitter.domain.entities.User
import timber.log.Timber

class HomeViewModel(private val showHome: ShowHome) : ViewModel() {

    init {
        // TODO: Adapt to SessionRepository
        showHome("")
    }

    private var disposable: Disposable? = null
    val navigation: MutableLiveData<String> = MutableLiveData()
    val user : MutableLiveData<User> = MutableLiveData()

    private fun showHome(nickname: String) {
        disposable = showHome.execute(nickname)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
            },{
                user.value = it
            })
    }

    fun follow(follow: String){

    }
}