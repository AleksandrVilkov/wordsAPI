package app.repository

import app.entity.WordEntity
import org.springframework.data.repository.CrudRepository

interface WordRepository : CrudRepository<WordEntity, Int> {
}