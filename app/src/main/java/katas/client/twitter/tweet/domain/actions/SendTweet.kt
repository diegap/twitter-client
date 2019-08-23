package katas.client.twitter.tweet.domain.actions

import io.reactivex.Completable
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.tweet.domain.entities.Tweet
import katas.client.twitter.tweet.domain.repositories.TweetRepository

class SendTweet(
    private val tweetRepository: TweetRepository,
    private val sessionRepository: LocalUserRepository
) {
    fun execute(tweet: String): Completable =
        sessionRepository.find()
            .map { it.nickname }
            .map { Tweet(nickname = it, content = tweet) }
            .flatMapCompletable { tweetRepository.save(it) }
}