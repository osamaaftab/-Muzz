package com.osamaaftab.muzz.di

import com.google.mlkit.nl.smartreply.SmartReply
import com.osamaaftab.muzz.data.repository.source.local.ChatLocalDataSource
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val LocalDataSourceModule = module {
    single { provideChatLocalDataSource(get()) }
}

fun provideChatLocalDataSource(realmConfig: RealmConfiguration): ChatLocalDataSource {
    return ChatLocalDataSource(Realm.open(realmConfig), SmartReply.getClient())
}