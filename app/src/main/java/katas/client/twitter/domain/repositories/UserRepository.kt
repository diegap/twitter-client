package katas.client.twitter.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.domain.entities.User

interface UserRepository {
    fun save(user: User) : Completable
    fun find(nickname: String) : Single<User>
}