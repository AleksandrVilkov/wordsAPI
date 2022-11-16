package app.repository

import app.entity.GameEntity
import org.springframework.data.repository.CrudRepository

interface GameRepository : CrudRepository<GameEntity, Int> {
}