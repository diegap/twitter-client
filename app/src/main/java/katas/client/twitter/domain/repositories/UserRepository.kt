package katas.client.twitter.domain.repositories

import io.reactivex.Completable
import katas.client.twitter.domain.entities.User

interface UserRepository {
    fun save(user: User) : Completable

}