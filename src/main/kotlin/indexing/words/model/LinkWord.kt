package org.example.indexing.words.model

import org.example.indexing.pages.model.LinkRef
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("link_words")
data class LinkWord(
    @Id
    val id: Long = 0,
    val wordRef: WordRef,
    val linkRef: LinkRef
)
