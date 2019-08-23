package katas.client.twitter.tweet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import katas.client.twitter.R
import katas.client.twitter.koinProxy
import katas.client.twitter.tweet.ui.viewmodel.TweetViewModel
import kotlinx.android.synthetic.main.dialog_fragment_tweet.*

object TweetDialogViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        koinProxy.get<TweetViewModel>() as T
}

class TweetDialogFragment : DialogFragment() {

    private lateinit var tweetViewModel: TweetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tweetViewModel = ViewModelProvider(
            this,
            TweetDialogViewModelFactory
        ).get(
            TweetViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_tweet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendTweetButton.setOnClickListener {
            tweetViewModel.sendTweet(tweetEditText.text.toString())
        }
    }

}