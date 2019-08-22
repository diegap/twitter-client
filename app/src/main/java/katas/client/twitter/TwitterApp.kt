package katas.client.twitter

import android.app.Application
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import katas.client.twitter.profile.domain.actions.FollowUser
import katas.client.twitter.profile.domain.actions.ShowHome
import katas.client.twitter.signup.infra.repositories.RestUserRepository
import katas.client.twitter.signup.infra.repositories.SessionRepository
import katas.client.twitter.signup.infra.repositories.UserEndpoint
import katas.client.twitter.signup.domain.actions.RegisterUser
import katas.client.twitter.signup.domain.repositories.RemoteUserRepository
import katas.client.twitter.profile.ui.viewmodel.HomeViewModel
import katas.client.twitter.signup.ui.viewmodel.SignupViewModel
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import timber.log.Timber.DebugTree
import timber.log.Timber.plant

internal object KoinProxy : KoinComponent

internal val koinProxy = KoinProxy.getKoin()

class TwitterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
        startKoin {

            modules(
                module {

                    val retrofit =
                        Retrofit.Builder().baseUrl("http://10.10.62.127:8080/api/v1/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                            .build()

                    single {
                        SessionRepository(this@TwitterApp) as katas.client.twitter.signup.domain.repositories.LocalUserRepository
                    }

                    single {
                        RestUserRepository(
                            retrofit.create(
                                UserEndpoint::class.java
                            )
                        ) as RemoteUserRepository
                    }

                    single {
                        ShowHome(get(), get())
                    }

                    single {
                        RegisterUser(get(), get())
                    }

                    single {
                        FollowUser(get(), get())
                    }

                    factory {
                        SignupViewModel(get())
                    }

                    factory {
                        HomeViewModel(get(), get())
                    }

                })
        }
    }
}