package katas.client.twitter.feature

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import katas.client.twitter.signup.domain.actions.RegisterUser
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import org.junit.Test

class RegisterUserTest {

    @Test
    fun `Registering a new user`() {

        // given
        val user =
            User("Jack Bauer", "@jack", emptySet())

        val userRepository = mock<LocalUserRepository> {
            on { save(user) } doReturn Completable.complete()
        }

        val remoteUserRepository = mock<RemoteUserRepository> {
            on { save(user) } doReturn Completable.complete()
        }

        // when
        val registerUser = RegisterUser(
            remoteUserRepository,
            userRepository
        )
        registerUser.execute(user.realName, user.nickname)

        // then
        verify(remoteUserRepository, times(1)).save(eq(user))
        verify(userRepository, times(1)).save(eq(user))

    }

}