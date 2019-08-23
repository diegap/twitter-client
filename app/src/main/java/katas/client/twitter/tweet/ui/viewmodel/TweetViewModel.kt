package katas.client.twitter.tweet.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import katas.client.twitter.tweet.domain.actions.SendTweet
import timber.log.Timber

class TweetViewModel(private val sendTweet: SendTweet) : ViewModel() {

    private var disposable: Disposable? = null
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    private val navigation: MutableLiveData<String> = MutableLiveData()

    fun sendTweet(tweetContent: String) {
        disposable = sendTweet.execute(tweetContent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
                Timber.e(it)
                errorMessage.value = "Error sending tweet $tweetContent"
            }, {
                Timber.d("Tweeted >> $tweetContent")
                navigation.value = "tweets"
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}