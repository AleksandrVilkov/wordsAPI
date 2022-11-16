package app.repository

import app.entity.GameEntity
import app.entity.UserEntity
import app.entity.WordEntity
import org.springframework.data.repository.CrudRepository
import java.util.Locale.IsoCountryCode

interface UserRepository : CrudRepository<UserEntity, Int> {
    fun findByLogin(login: String): List<UserEntity>
}

interface GameRepository : CrudRepository<GameEntity, Int> {
    fun findByUserId(userId: Int): List<GameEntity>
}

interface WordRepository : CrudRepository<WordEntity, Int> {
    fun findByCountletters(count: Int): List<WordEntity>
    fun findByValue(value: String): List<WordEntity>
}