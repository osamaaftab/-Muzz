package com.osamaaftab.muzz.di

import com.osamaaftab.muzz.data.repository.ChatRepositoryImp
import com.osamaaftab.muzz.data.repository.ErrorHandle
import com.osamaaftab.muzz.data.repository.source.local.ChatLocalDataSource
import com.osamaaftab.muzz.domain.repository.ChatRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single { provideChatRepository(get(), get()) }
}

fun provideChatRepository(
    localDataSource: ChatLocalDataSource,
    errorHandle: ErrorHandle
): ChatRepository {
    return ChatRepositoryImp(localDataSource, errorHandle)
}