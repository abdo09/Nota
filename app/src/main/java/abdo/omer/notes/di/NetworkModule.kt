package abdo.omer.notes.di


import abdo.omer.notes.data.client.createHttpClient
import abdo.omer.notes.data.client.createRetrofit
import abdo.omer.notes.data.client.createWebService
import abdo.omer.notes.data.client.AuthInterceptor
import abdo.omer.notes.data.services.UserServices
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module


val networkModule = module {
    //create webservices definitions
    single {
        AuthInterceptor(androidApplication())
    }
    single {
        createHttpClient(get())
    }
    single(named("noauth")) {
        createHttpClient(get(), false)
    }

    single { createRetrofit(get()) }

    single(named("noauth")) {
        val httpClient by inject<OkHttpClient>(named("noauth"))
        createRetrofit(httpClient)
    }
    factory { createWebService<UserServices>(get()) }
}

