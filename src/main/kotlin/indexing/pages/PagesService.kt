package org.example.indexing.pages

import org.example.indexing.pages.model.LinkBetweenUrl
import org.example.indexing.pages.model.Url
import org.example.indexing.words.LinkWordsRepo
import org.example.indexing.words.WordsParser
import org.example.indexing.words.model.LinkWord
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException

@Service
@Transactional
open class PagesService(
    private val urlsRepo: URLsRepo,
    private val linksBetweenUrlRepo: LinksBetweenUrlRepo,
    private val linkWordsRepo: LinkWordsRepo,
    private val wordsParser: WordsParser
) {

    open fun urlAnalyze(url: Url) {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.info("indexing $url")

        try {
            val doc = Jsoup.connect(url.url).get()
            val extractedLinks = extractLinks(doc, url)
            for (extractedLink in extractedLinks) {
                handleExtractedLink(extractedLink, url)
            }
            handleExtractedPageText(doc.text(), url)
        } catch (ex: IOException) {
            println("IOException on $url")
        }

        logger.info("finished $url")
    }

    private fun handleExtractedLink(
        extractedLink: LinkDto,
        url: Url
    ) {
        if (isSocialMediaLink(extractedLink.href)) {
            return
        }

        val newUrlEntity = urlsRepo.saveIgnoringDuplicate(extractedLink.href) ?: return
        val linkWordIds = wordsParser.parseText(url, extractedLink.text)
        val link = saveLink(url, newUrlEntity)

        val linkWords = linkWordIds.map {
            LinkWord(
                wordRef = AggregateReference.to(it),
                linkRef = AggregateReference.to(link.id)
            )
        }
        linkWordsRepo.saveAll(linkWords)

        return
    }

    private fun isSocialMediaLink(link: String): Boolean {
        return when {
            link.startsWith("http://www.youtube.com") -> true
            link.startsWith("https://t.me") -> true
            link.startsWith("https://vk.com") -> true
            else -> false
        }
    }

    private fun handleExtractedPageText(pageText: String?, url: Url) {
        if (pageText != null) {
            wordsParser.parsePageText(url, pageText)
        }
    }

    private fun saveLink(fromUrl: Url, toUrl: Url): LinkBetweenUrl {
        return linksBetweenUrlRepo.save(
            LinkBetweenUrl(
                fromUrlRef = AggregateReference.to(fromUrl.id),
                toUrlRef = AggregateReference.to(toUrl.id)
            )
        )
    }

    private fun extractLinks(doc: Document, url: Url): List<LinkDto> {
        val extractedLinks = mutableListOf<LinkDto>()

        val links = doc.select("a[href]")
        for (link in links) {
            val linkValue = extractLinkValue(link, url)
            if (linkValue != null && linkValue.text.isNotEmpty()) {
                extractedLinks.add(linkValue)
            }
        }

        return extractedLinks
    }

    private fun extractLinkValue(link: Element, url: Url): LinkDto? {
        var href = link.attr("href")
        val text = link.text()

        if (href.startsWith("/")) {
            href = url.url.substring(0, url.url.indexOf(".ru") + 3).plus(href)
        }

        if (!href.startsWith("http")) {
            return null
        }

        return LinkDto(href, text)
//        println("Link: $href, Text: $text")
    }
}