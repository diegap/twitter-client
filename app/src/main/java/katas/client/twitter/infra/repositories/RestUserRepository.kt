package katas.client.twitter.infra.repositories

import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.RemoteUserRepository
import katas.client.twitter.domain.repositories.UserRepository
import katas.client.twitter.infra.repositories.endpoints.RestUser
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint
import katas.client.twitter.infra.repositories.endpoints.UserFollow

internal class RestUserRepository(private val userEndpoint: UserEndpoint) : RemoteUserRepository {

    override fun find(nickname: String): Single<User> {
        return userEndpoint.getUser(nickname).map {
            User(realName = it.realName, nickname = it.nickname, follows = it.follows.toSet())
        }
    }

    override fun save(user: User): Completable {
        return userEndpoint.registerUser(RestUser.from(user)).ignoreElement()
    }

    override fun follow(nickname: String, follow: String) : Completable {
        return userEndpoint.followUser(nickname, UserFollow(follow)).ignoreElement()
    }

    override fun tweet(nickname: String, tweet: String) : Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}