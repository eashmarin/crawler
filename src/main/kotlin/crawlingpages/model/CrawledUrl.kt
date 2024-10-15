package org.example.crawlingpages.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("crawled_urls")
data class CrawledUrl(
    @Id
    val id: Long = 0,
    val url: String
)
