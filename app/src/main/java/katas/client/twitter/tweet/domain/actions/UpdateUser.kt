package katas.client.twitter.tweet.domain.actions

import io.reactivex.Completable
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.LocalUserRepository
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository

class UpdateUser(
    private val remoteUserRepository: RemoteUserRepository,
    private val localUserRepository: LocalUserRepository
) {
    fun execute(nickname: String, userName: String): Completable {
        return remoteUserRepository.find(nickname)
            .flatMapCompletable {
                remoteUserRepository.update(it.copy(realName = userName))
            }.doOnComplete {
                localUserRepository.save(
                    User(
                        realName = userName,
                        nickname = nickname,
                        follows = emptySet()
                    )
                )
            }
    }
}