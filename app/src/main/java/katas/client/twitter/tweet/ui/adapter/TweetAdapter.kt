package katas.client.twitter.tweet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import katas.client.twitter.R
import katas.client.twitter.tweet.domain.entities.Tweet
import kotlinx.android.synthetic.main.layout_tweet.view.*

class TweetAdapter :
    RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    private val tweets = mutableListOf<Tweet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        return TweetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_tweet, parent, false)
        )
    }

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.view.tv_tweet_content.text = tweet.content
    }

    fun updateTweets(newTweets: List<Tweet>) {
        tweets.clear()
        tweets.addAll(newTweets)
        notifyDataSetChanged()
    }

    class TweetViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}