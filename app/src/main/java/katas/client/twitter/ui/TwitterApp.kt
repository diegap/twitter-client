package katas.client.twitter.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import katas.client.twitter.BuildConfig
import katas.client.twitter.domain.actions.FollowUser
import katas.client.twitter.domain.actions.RegisterUser
import katas.client.twitter.domain.actions.ShowHome
import katas.client.twitter.domain.repositories.RemoteUserRepository
import katas.client.twitter.domain.repositories.UserRepository
import katas.client.twitter.infra.repositories.LocalUserRepository
import katas.client.twitter.infra.repositories.RestUserRepository
import katas.client.twitter.infra.repositories.endpoints.UserEndpoint
import katas.client.twitter.ui.viewmodel.HomeViewModel
import katas.client.twitter.ui.viewmodel.SignupViewModel
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
                        LocalUserRepository(this@TwitterApp) as UserRepository
                    }

                    single {
                        RestUserRepository(retrofit.create(UserEndpoint::class.java)) as RemoteUserRepository
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