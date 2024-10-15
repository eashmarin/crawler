package org.example.crawlingpages

import jakarta.annotation.PostConstruct
import org.example.crawlingpages.model.CrawledUrl
import org.example.indexing.pages.LinksBetweenUrlRepo
import org.example.indexing.pages.PagesService
import org.example.indexing.pages.URLsRepo
import org.example.indexing.words.LinkWordsRepo
import org.example.indexing.words.WordLocationsRepo
import org.example.indexing.words.WordsRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CrawlingPagesService(
    private val urlsRepo: URLsRepo,
    private val pagesService: PagesService,
    private val crawledUrlsRepo: CrawledUrlsRepo,
    private val linksBetweenUrlRepo: LinksBetweenUrlRepo,
    private val wordsRepo: WordsRepo,
    private val wordLocationsRepo: WordLocationsRepo,
    private val linkWordsRepo: LinkWordsRepo
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    fun crawl() {
        for (depth in 1..2) {
            val urls = urlsRepo.findAll()

            val crawledUrls = crawledUrlsRepo.findAll().map { it.url }.toHashSet()
            val notCrawledUrls = urls.filter { !crawledUrls.contains(it.url) }

            for (url in notCrawledUrls) {
                pagesService.urlAnalyze(url)
                crawledUrlsRepo.save(CrawledUrl(url = url.url))
                logInfo()
            }
        }
    }

    private fun logInfo() {
        logger.info(tableInfoString("crawled_urls", crawledUrlsRepo.findAll().count()))
        logger.info(tableInfoString("words", wordsRepo.findAll().count()))
        logger.info(tableInfoString("word_locations", wordLocationsRepo.findAll().count()))
        logger.info(tableInfoString("link_words", linkWordsRepo.findAll().count()))
        logger.info(tableInfoString("links_between_url", linksBetweenUrlRepo.findAll().count()))
    }

    private fun tableInfoString(name: String, size: Int): String {
        return "$name [size = $size]"
    }

}