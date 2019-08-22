package katas.client.twitter.profile.domain.actions

import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.signup.domain.repositories.LocalUserRepository

class FollowUser(
    private val restUserRepository: RemoteUserRepository,
    private val userRepository: LocalUserRepository
) {
    fun execute(nickname: String, follow: String): Single<User> =
        restUserRepository
            .follow(nickname, follow)
            .andThen(
                userRepository.find(nickname)
                    .map { it.addFollow(follow) }
                    .flatMap { userRepository.save(it).toSingle { it } }
            )
}