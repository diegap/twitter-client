package katas.client.twitter.domain.actions

import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository

class RegisterUser(private val userRepository: UserRepository) {

    fun execute(user: User) {
        userRepository.save(user)
    }
}