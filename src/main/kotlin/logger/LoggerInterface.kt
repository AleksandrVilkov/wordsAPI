package logger

interface LoggerInterface {
    fun info(msg: String)

    fun debug(msg: String)

    fun error(msg: String)
    fun warn(msg: String)

    fun setNameClass(name: String)
}
