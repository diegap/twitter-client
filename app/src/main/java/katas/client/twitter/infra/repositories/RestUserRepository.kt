package katas.client.twitter.infra.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository
import katas.client.twitter.infra.repositories.endpoints.RestUser
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint

class RestUserRepository(private val userEndpoint: UserEndpoint) : UserRepository {

    override fun find(nickname: String): Single<User> {
        return userEndpoint.getUser(nickname).map {
            User(realName = it.realName, nickname = it.nickname, follows = it.follows.toSet())
        }
    }

    override fun save(user: User): Completable {
        return userEndpoint.registerUser(RestUser.from(user)).ignoreElement()
    }
}