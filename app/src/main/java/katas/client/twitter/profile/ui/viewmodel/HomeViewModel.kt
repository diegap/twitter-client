package katas.client.twitter.profile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import katas.client.twitter.profile.domain.actions.FollowUser
import katas.client.twitter.profile.domain.actions.GetTweets
import katas.client.twitter.profile.domain.actions.ShowHome
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.tweet.domain.actions.UpdateUser
import katas.client.twitter.tweet.domain.entities.Tweet
import timber.log.Timber

class HomeViewModel(
    private val showHome: ShowHome,
    private val followUser: FollowUser,
    private val updateUser: UpdateUser,
    private val getTweets: GetTweets
) :
    ViewModel() {

    init {
        showHome()
        getTweets()
    }

    private var disposable: Disposable? = null

    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()
    val tweets: MutableLiveData<List<Tweet>> = MutableLiveData()

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

    fun updateUser(nickname: String, userName: String) {
        disposable = updateUser.execute(nickname, userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
                errorMessage.value = "Error updating user $nickname"
            }, {
                Timber.d("Updated user >> $nickname with realname $userName")
            })
    }

    private fun getTweets() {
        disposable = getTweets.execute()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
            }, {
                Timber.d("Retrieved ${it.size} tweets")
                tweets.value = it
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}