package com.osamaaftab.muzz.app

import android.app.Application
import com.osamaaftab.muzz.di.AppModule
import com.osamaaftab.muzz.di.LocalDataSourceModule
import com.osamaaftab.muzz.di.RepositoryModule
import com.osamaaftab.muzz.di.UseCaseModule
import com.osamaaftab.muzz.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    AppModule,
                    RepositoryModule,
                    UseCaseModule,
                    ViewModelModule,
                    LocalDataSourceModule,
                )
            )
        }
    }
}