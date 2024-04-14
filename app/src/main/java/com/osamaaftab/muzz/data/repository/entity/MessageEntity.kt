package com.osamaaftab.muzz.data.repository.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

open class MessageEntity : RealmObject {

    @PrimaryKey
    var id: String = ""
    var messageType: String = ""
    var messageContent: String = ""
    var time: String = ""

    // Default constructor required by Realm
    constructor()

    constructor(
        id: String = UUID.randomUUID().toString(),
        messageType: String,
        messageContent: String,
        time: String = System.currentTimeMillis().toString()
    ) {
        this.id = id
        this.messageType = messageType
        this.messageContent = messageContent
        this.time = time
    }
}