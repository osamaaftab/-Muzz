package com.osamaaftab.muzz.presentation.event

sealed class ChatEvent {
    data class MessageChanged(val message: String) : ChatEvent()
    data object SendMessage : ChatEvent()
}