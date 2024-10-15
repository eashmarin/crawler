package org.example.indexing.pages.model

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Table

typealias UrlRef = AggregateReference<Url, Long>

@Table("urls")
data class Url(
    @Id
    val id: Long = 0,
    var url: String,
)
