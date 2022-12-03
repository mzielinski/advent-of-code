package com.mzielinski.aoc2022

import java.net.URL

object GodUtil {

    fun readFile(fileName: String): List<String> {
        val resource: URL? = this::class.java.getResource(fileName)
        val readText: String? = resource?.readText(Charsets.UTF_8)
        return readText?.lines() ?: emptyList()
    }
}