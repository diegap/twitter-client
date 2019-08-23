package katas.client.twitter.tweet.domain.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import katas.client.twitter.tweet.domain.entities.Tweet

interface TweetRepository {
    fun save(tweet: Tweet): Completable
    fun find(nickname: String): Observable<Tweet>
}