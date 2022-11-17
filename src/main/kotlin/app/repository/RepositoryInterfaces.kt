package app.repository

import app.entity.GameEntity
import app.entity.UserEntity
import app.entity.WordEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Int> {
    fun findByLogin(login: String): List<UserEntity>
}

interface GameRepository : CrudRepository<GameEntity, Int> {
    fun findByUserId(userId: Int): List<GameEntity>
}

interface WordRepository : CrudRepository<WordEntity, Int> {
    fun findByCountLetters(count: Int): List<WordEntity>
    fun findByWordValue(value: String): List<WordEntity>
}