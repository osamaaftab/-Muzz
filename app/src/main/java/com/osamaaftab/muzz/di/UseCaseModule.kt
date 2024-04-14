package com.osamaaftab.muzz.di

import com.osamaaftab.muzz.domain.repository.ChatRepository
import com.osamaaftab.muzz.domain.usecase.GetChatMessagesUseCase
import com.osamaaftab.muzz.domain.usecase.SendMessageUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single { provideGetChatMessagesUseCase(get()) }
    single { provideSendMessageUseCase(get()) }

}

private fun provideGetChatMessagesUseCase(
    chatRepository: ChatRepository
): GetChatMessagesUseCase {
    return GetChatMessagesUseCase(chatRepository)
}

private fun provideSendMessageUseCase(
    chatRepository: ChatRepository,
): SendMessageUseCase {
    return SendMessageUseCase(chatRepository)
}