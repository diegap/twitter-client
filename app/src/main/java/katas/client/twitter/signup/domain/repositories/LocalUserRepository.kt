package katas.client.twitter.signup.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User

interface LocalUserRepository {
    fun save(user: User) : Completable
    fun find() : Single<User>
}