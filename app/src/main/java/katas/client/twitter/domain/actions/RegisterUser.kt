package katas.client.twitter.domain.actions

import io.reactivex.Completable
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository

class RegisterUser(private val userRepository: UserRepository) {

    fun execute(userName: String, nickname: String): Completable {
        val user = User(userName, nickname)
        return userRepository.save(user)
    }
}