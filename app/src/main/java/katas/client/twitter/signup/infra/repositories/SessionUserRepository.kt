package katas.client.twitter.signup.infra.repositories

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import katas.client.twitter.signup.domain.repositories.LocalUserRepository

class SessionUserRepository(private val context: Context) :
    LocalUserRepository {

    override fun find(): Single<User> {
        val loggedUser = User(
            nickname = getUserPreferences().getString("nickname", "")!!,
            realName = getUserPreferences().getString("realName", "")!!,
            follows = emptySet()
        )
        return Single.just(loggedUser)
    }

    override fun save(user: User): Completable {
        return Completable.fromRunnable {
            getUserPreferences().edit()
                .putString("nickname", user.nickname)
                .putString("realName", user.realName)
                .apply()
        }
    }

    private fun getUserPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            "katas.client.twitter.localUserRepository",
            Context.MODE_PRIVATE
        )
    }
}
