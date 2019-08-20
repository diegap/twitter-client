package katas.client.twitter.feature

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import katas.client.twitter.domain.actions.RegisterUser
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository
import org.junit.Test

class RegisterUserTest {

    @Test
    fun `Registering a new user`() {

        // given
        val user = User("Jack Bauer", "@jack")

        val userRepository = mock<UserRepository> {
            on { save(user) } doReturn Completable.complete()
        }

        // when
        val registerUser = RegisterUser(userRepository)
        registerUser.execute(user.realName, user.nickname)

        // then
        verify(userRepository, times(1)).save(eq(user))

    }

}