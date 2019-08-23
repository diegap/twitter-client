package katas.client.twitter.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Completable
import katas.client.twitter.RxSchedulersRules
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.signup.domain.actions.RegisterUser
import katas.client.twitter.signup.ui.viewmodel.SignupViewModel
import org.amshove.kluent.*
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class SignupViewModelTest {

    @get:Rule
    val rxRule = RxSchedulersRules()

    @get:Rule
    val androidTest = InstantTaskExecutorRule()

    @Test
    fun testSignup(){
        // given
        val remoteUserRepository = mock<RemoteUserRepository>{
            on { save(any()) } doReturn Completable.complete()
        }
        val localUserRepository = mock<LocalUserRepository>{
            on { save(any()) } doReturn Completable.complete()
        }

        val action = RegisterUser(remoteUserRepository, localUserRepository)
        val viewModel = SignupViewModel(action)

        // when
        viewModel.signup("jack", "@jack")

        // then
        viewModel.navigation.value shouldEqual "tweets"
        viewModel.errorMessage.value shouldEqual null

        verify(remoteUserRepository, atMost(1)).save(any())
        verify(localUserRepository, atMost(1)).find()
    }

    @Test
    fun testFailedSignup(){
        // given
        val remoteUserRepository = mock<RemoteUserRepository>{
            on { save(any()) } doReturn Completable.error(RuntimeException("User already registered!"))
        }
        val localUserRepository = mock<LocalUserRepository>{
            on { save(any()) } doReturn Completable.complete()
        }

        val action = RegisterUser(remoteUserRepository, localUserRepository)
        val viewModel = SignupViewModel(action)

        // when
        viewModel.signup("jack", "@jack")

        // then
        viewModel.navigation.value shouldEqual null
        viewModel.errorMessage.value?.`should start with`("Error registering user")

        verify(remoteUserRepository, atMost(1)).save(any())
        verify(localUserRepository, atMost(1)).find()
    }
}