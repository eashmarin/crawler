package org.example.indexing.pages.model

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.data.relational.core.mapping.Table

typealias LinkRef = AggregateReference<LinkBetweenUrl, Long>

@Table("links_between_url")
data class LinkBetweenUrl(
    @Id
    val id: Long = 0,
    val fromUrlRef: UrlRef,
    val toUrlRef: UrlRef
)
