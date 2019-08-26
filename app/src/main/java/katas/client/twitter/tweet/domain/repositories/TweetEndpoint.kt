package katas.client.twitter.tweet.domain.repositories

import io.reactivex.Single
import katas.client.twitter.tweet.domain.entities.Tweet
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface TweetEndpoint {
    @POST("tweets/")
    fun save(@Body tweet: RestTweet): Single<ResponseBody>

    @GET("tweets/{nickname}")
    fun find(@Path("nickname") nickname: String): Single<List<RestTweet>>
}

internal data class RestTweet(val nickname: String, val content: String) {
    companion object {
        fun from(tweet: Tweet): RestTweet =
            RestTweet(nickname = tweet.nickname, content = tweet.content)
    }
}
