package com.osamaaftab.muzz.di

import com.osamaaftab.muzz.data.repository.ErrorHandle
import com.osamaaftab.muzz.data.repository.entity.MessageEntity
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val AppModule = module {
    single { provideError() }
    single { provideRealMConfiguration() }
}

fun provideError(): ErrorHandle {
    return ErrorHandle()
}


fun provideRealMConfiguration(): RealmConfiguration {
    return RealmConfiguration.create(
        schema = setOf(
            MessageEntity::class,
        ),
    )
}