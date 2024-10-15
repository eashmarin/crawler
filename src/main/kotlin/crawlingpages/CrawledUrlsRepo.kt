package org.example.crawlingpages

import org.example.crawlingpages.model.CrawledUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CrawledUrlsRepo : CrudRepository<CrawledUrl, Long>