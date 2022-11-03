package controller

import model.Entity.Message

fun checkCount(count: Int): Boolean {
    //TODO вынести в настройки
    return count in 4..6
}

fun getDescription(msgs: List<Message>): String {
    var description = ""
    for (msg in msgs) {
        description += "${msg.textMessage}\n"
    }
    return description
}