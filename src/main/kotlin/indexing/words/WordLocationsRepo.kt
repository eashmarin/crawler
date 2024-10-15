package org.example.indexing.words

import org.example.indexing.words.model.WordLocation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordLocationsRepo : CrudRepository<WordLocation, Long>