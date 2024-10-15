package org.example.indexing.words.model

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Table


typealias WordRef = AggregateReference<Word, Long>

@Table("words")
data class Word(
    @Id
    val id: Long = 0,
    val word: String,
    val isFiltered: Boolean
)
