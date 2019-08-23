package katas.client.twitter.signup.infra.repositories

import io.reactivex.Single
import katas.client.twitter.signup.domain.entities.User
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class RestUser(val realName: String, val nickname: String, val follows: List<String>) {
    companion object {
        fun from(user: User): RestUser {
            return RestUser(
                user.realName,
                user.nickname,
                user.follows.toList()
            )
        }
    }
}

data class UserFollow(val nickname: String)

internal interface UserEndpoint {
    @POST("users/")
    fun registerUser(@Body user: RestUser): Single<ResponseBody>

    @GET("users/{nickname}")
    fun getUser(@Path("nickname") nickname: String): Single<RestUser>

    @POST("users/{nickname}/follows")
    fun followUser(
        @Path("nickname") nickname: String,
        @Body follow: UserFollow
    ): Single<ResponseBody>
}