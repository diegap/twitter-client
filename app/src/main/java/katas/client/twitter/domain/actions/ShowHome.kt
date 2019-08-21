package katas.client.twitter.domain.actions

import io.reactivex.Single
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository

class ShowHome(private val restUserRepository: UserRepository,
               private val localUserRepository: UserRepository
) {
    fun execute(nickname: String) : Single<User> =
         localUserRepository.find(nickname).flatMap {
            restUserRepository.find(it.nickname)
     }
}