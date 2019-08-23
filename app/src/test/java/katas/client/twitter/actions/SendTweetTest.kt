package katas.client.twitter.actions

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.tweet.domain.actions.SendTweet
import katas.client.twitter.tweet.domain.entities.Tweet
import katas.client.twitter.tweet.domain.repositories.TweetRepository
import org.junit.Test

class SendTweetTest {

    @Test
    fun `send a new tweet`() {

        // given
        val tweet = Tweet(nickname = "@jack", content = "This is a tweet!")
        val user = User(nickname = "@jack", realName = "Jack", follows = emptySet())

        val sessionRepository = mock<LocalUserRepository> {
            on { find() } doReturn Single.just(user)
        }

        val remoteTweetRepository = mock<TweetRepository> {
            on { save(any()) } doReturn Completable.complete()
        }

        val sendTweet = SendTweet(
            sessionRepository = sessionRepository,
            tweetRepository = remoteTweetRepository
        )

        // when
        sendTweet.execute(tweet.content)

        // then
        verify(sessionRepository, atMost(1)).find()
        verify(remoteTweetRepository, atMost(1)).save(eq(tweet))

    }
}