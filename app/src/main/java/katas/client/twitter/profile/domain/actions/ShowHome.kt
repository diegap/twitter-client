package katas.client.twitter.profile.domain.actions

import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.signup.domain.repositories.LocalUserRepository

class ShowHome(private val restUserRepository: RemoteUserRepository,
               private val localUserRepository: LocalUserRepository
) {
    fun execute() : Single<User> =
         localUserRepository.find().flatMap {
            restUserRepository.find(it.nickname)
     }
}