package org.example.indexing.words.model

import org.example.indexing.pages.model.UrlRef
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("word_locations")
data class WordLocation(
    @Id
    val id: Long = 0,
    val wordRef: WordRef,
    val urlRef: UrlRef,
    val location: Int
)
