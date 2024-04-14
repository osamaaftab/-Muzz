package com.osamaaftab.muzz.domain.model

data class ChatItemModel<T : ChatData>(var chatItemType: ChatItemType, var data: T)