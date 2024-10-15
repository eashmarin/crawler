package org.example.indexing.words

import org.example.indexing.words.model.LinkWord
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkWordsRepo : CrudRepository<LinkWord, Long>