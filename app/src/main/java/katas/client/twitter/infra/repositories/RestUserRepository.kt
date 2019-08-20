package katas.client.twitter.infra.repositories

import io.reactivex.Completable
import katas.client.twitter.domain.entities.User
import katas.client.twitter.domain.repositories.UserRepository
import katas.client.twitter.infra.repositories.endpoints.RestUser
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint

class RestUserRepository(private val userEndpoint: UserEndpoint) : UserRepository {
    override fun save(user: User): Completable {
        return userEndpoint.registerUser(RestUser.from(user)).ignoreElement()
    }
}