package katas.client.twitter.signup.domain.actions

import io.reactivex.Completable
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.signup.domain.repositories.LocalUserRepository

class RegisterUser(
    private val remoteUserRepository: RemoteUserRepository,
    private val localUserRepository: LocalUserRepository
) {

    fun execute(userName: String, nickname: String): Completable {
        val user = User(userName, nickname, emptySet())
        return remoteUserRepository
            .save(user)
            .doOnComplete { localUserRepository.save(user) }
            .doOnError { throw RuntimeException("Cannot register user $nickname") }
    }
}