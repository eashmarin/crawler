package org.example.indexing.pages

import org.example.indexing.pages.model.Url
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface URLsRepo : CrudRepository<Url, Long> {
    @Query(value = "INSERT INTO urls (url) VALUES (:url) ON CONFLICT (url) DO NOTHING RETURNING *")
    fun saveIgnoringDuplicate(@Param("url") url: String): Url?
}