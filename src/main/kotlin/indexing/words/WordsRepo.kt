package org.example.indexing.words

import org.example.indexing.words.model.Word
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsRepo : CrudRepository<Word, Long>