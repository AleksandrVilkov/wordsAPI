package controller

fun checkCount(count: Int): Boolean {
    //TODO вынести в настройки
    return count in 4..6
}