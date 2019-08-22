package katas.client.twitter.domain.actions

import io.reactivex.Completable
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.RemoteUserRepository
import katas.client.twitter.domain.repositories.UserRepository

class RegisterUser(
    private val restUserRepository: RemoteUserRepository,
    private val localUserRepository: UserRepository
) {

    fun execute(userName: String, nickname: String): Completable {
        val user = User(userName, nickname, emptySet())
        return restUserRepository.save(user).andThen(localUserRepository.save(user))
    }
}