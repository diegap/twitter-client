package katas.client.twitter.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.RxSchedulersRules
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.tweet.domain.actions.SendTweet
import katas.client.twitter.tweet.domain.entities.Tweet
import katas.client.twitter.tweet.domain.repositories.TweetRepository
import katas.client.twitter.tweet.ui.viewmodel.TweetViewModel
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldStartWith
import org.junit.Rule
import org.junit.Test

class TweetViewModelTest {

    @get:Rule
    val rxRule = RxSchedulersRules()

    @get:Rule
    val androidTest = InstantTaskExecutorRule()

    @Test
    fun `send a tweet`(){

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

        val viewModel = TweetViewModel(sendTweet)

        // when
        viewModel.sendTweet(tweet.content)

        // then
        viewModel.navigation.value shouldEqual "tweets"
        viewModel.errorMessage.value shouldEqual null

        verify(sessionRepository, atMost(1)).find()
        verify(remoteTweetRepository, atMost(1)).save(eq(tweet))
    }

    @Test
    fun `send tweet fails`(){

        val tweet = Tweet(nickname = "@jack", content = "This is a tweet!")
        val user = User(nickname = "@jack", realName = "Jack", follows = emptySet())

        val sessionRepository = mock<LocalUserRepository> {
            on { find() } doReturn Single.just(user)
        }

        val remoteTweetRepository = mock<TweetRepository> {
            on { save(any()) } doReturn Completable.error { RuntimeException("Error receiving tweet") }
        }

        val sendTweet = SendTweet(
            sessionRepository = sessionRepository,
            tweetRepository = remoteTweetRepository
        )

        val viewModel = TweetViewModel(sendTweet)

        // when
        viewModel.sendTweet(tweet.content)

        // then
        viewModel.navigation.value shouldEqual null
        viewModel.errorMessage.value?.shouldStartWith("Error sending tweet")

        verify(sessionRepository, atMost(1)).find()
        verify(remoteTweetRepository, atMost(1)).save(eq(tweet))

    }
}