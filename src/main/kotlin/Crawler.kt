package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Crawler

fun main(args: Array<String>) {
    runApplication<Crawler>(*args)
}