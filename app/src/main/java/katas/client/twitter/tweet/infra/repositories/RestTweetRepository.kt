package katas.client.twitter.tweet.infra.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.tweet.domain.entities.Tweet
import katas.client.twitter.tweet.domain.repositories.RestTweet
import katas.client.twitter.tweet.domain.repositories.TweetEndpoint
import katas.client.twitter.tweet.domain.repositories.TweetRepository

internal class RestTweetRepository(private val tweetEndpoint: TweetEndpoint) : TweetRepository {

    override fun find(nickname: String): Single<List<Tweet>> =
        tweetEndpoint.find(nickname).flatMap { tweet ->
            Single.just(tweet.map {
                Tweet(
                    nickname = it.nickname,
                    content = it.content
                )
            })
        }

    override fun save(tweet: Tweet): Completable {
        return tweetEndpoint.save(RestTweet.from(tweet)).ignoreElement()
    }
}