package katas.client.twitter.profile.domain.actions

import io.reactivex.Single
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.tweet.domain.entities.Tweet
import katas.client.twitter.tweet.domain.repositories.TweetRepository

class GetTweets(
    private val tweetRepository: TweetRepository,
    private val sessionUserRepository: LocalUserRepository
) {
    fun execute(): Single<List<Tweet>> =
        sessionUserRepository
            .find()
            .flatMap { tweetRepository.find(it.nickname) }
}