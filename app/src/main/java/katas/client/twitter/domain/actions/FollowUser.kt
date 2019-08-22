package katas.client.twitter.domain.actions

import io.reactivex.Single
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.RemoteUserRepository
import katas.client.twitter.domain.repositories.UserRepository

class FollowUser(
    private val restUserRepository: RemoteUserRepository,
    private val userRepository: UserRepository
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