package katas.client.twitter.signup.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User

interface RemoteUserRepository {

    fun save(user: User) : Completable

    fun find(nickname: String) : Single<User>

    fun follow(nickname: String, follow: String) : Completable

    fun tweet(nickname: String, tweet: String) : Completable
}