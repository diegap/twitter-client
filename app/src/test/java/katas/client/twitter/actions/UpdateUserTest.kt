package katas.client.twitter.actions

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.tweet.domain.actions.UpdateUser
import org.junit.Test

class UpdateUserTest {

    @Test
    fun `update username of a registered user`() {

        // given
        val user = User(nickname = "@jack", realName = "Jack", follows = emptySet())

        val remoteUserRepository = mock<RemoteUserRepository>() {
            on {
                find("@jack")
            } doReturn Single.just(user)

            on {
                update(user)
            } doReturn Completable.complete()
        }

        val localUserRepository = mock<LocalUserRepository>() {
            on {
                save(user)
            } doReturn Completable.complete()
        }

        val updateUser = UpdateUser(remoteUserRepository, localUserRepository)

        // when
        updateUser.execute(user.nickname, user.realName).blockingGet()

        // then
        with(remoteUserRepository){
            verify(this, atMost(1)).find(user.nickname)
            verify(this, times(1)).update(eq(user))
        }

        with(localUserRepository) {
            verify(this, never()).find()
            verify(this, atMost(1)).save(user)
        }


    }

}