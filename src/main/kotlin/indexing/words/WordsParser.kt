package org.example.indexing.words

import org.example.indexing.pages.model.Url
import org.example.indexing.words.model.Word
import org.example.indexing.words.model.WordLocation
import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
open class WordsParser(
    val wordsRepo: WordsRepo,
    val wordLocations: WordLocationsRepo
) {

    open fun parseText(url: Url, pageText: String): List<Long> {
        val words = separateWords(pageText)
        return saveWords(words, url, false)
    }

    open fun parsePageText(url: Url, pageText: String): List<Long> {
        val words = separateWords(pageText)
        return saveWords(words, url, true)
    }

    private fun saveWords(words: List<String>, url: Url, saveLocation: Boolean): List<Long> {
        val savedWords = mutableListOf<Long>()

        val wordsInRepo = wordsRepo.findAll().associateBy({ it.word }, { it.id }).toMutableMap()

        for ((index, word) in words.withIndex()) {
            val wordValue = word.lowercase()
            var wordId = wordsInRepo[wordValue]

            if (wordId == null) {
                val id = wordsRepo.save(
                    Word(
                        word = wordValue,
                        isFiltered = false
                    )
                ).id
                wordsInRepo[wordValue] = id
                wordId = id
            }

            if (saveLocation) {
                wordLocations.save(
                    WordLocation(
                        wordRef = AggregateReference.to(wordId),
                        urlRef = AggregateReference.to(url.id),
                        location = index
                    )
                )
            }

            savedWords.add(wordId)
        }

        return savedWords
    }

    private fun separateWords(text: String): List<String> {
        return Regex("[\\p{L}\\p{N}]+").findAll(text)
            .map { it.value }
            .toList()
    }
}