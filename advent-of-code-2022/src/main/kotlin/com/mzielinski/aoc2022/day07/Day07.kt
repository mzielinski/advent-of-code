package com.mzielinski.aoc2022.day07

class Day07 {

    sealed interface Item {
        fun getName(): String
    }

    class File(private val name: String, val size: Long) : Item {
        override fun getName(): String {
            return name
        }

        override fun toString(): String {
            return "File: $name ($size)"
        }
    }

    class Directory(private val name: String, val items: MutableMap<String, MutableList<Item>>) : Item {
        override fun getName(): String {
            return name
        }

        override fun toString(): String {
            return "Directory: $name  items=($items)"
        }

        fun directorySize(): Long {
            return items.values.flatMap { values ->
                val map = values.map {
                    when (it) {
                        is File -> it.size
                        is Directory -> it.directorySize()
                    }
                }
                map
            }.sum()
        }
    }

    fun noSpaceOnDevice(lines: List<String>, part: String): Long {
        val tree: MutableMap<String, MutableList<Item>> = buildTree(lines)
        val directoriesSize: MutableMap<String, Long> = mutableMapOf()
        findDirSizes(mutableListOf(), tree, directoriesSize)
        return when (part) {
            "01" -> directoriesSize.values.filter { it < 100000 }.toList().sum()
            "02" -> {
                val availableSize: Long = 70000000 - Directory("/", tree).directorySize()
                val sizes: List<Long> = directoriesSize.values.sorted()
                sizes.first { (it + availableSize) > 30000000 }
            }

            else -> -1
        }
    }

    private fun findDirSizes(
        path: MutableList<String>,
        tree: MutableMap<String, MutableList<Item>>,
        sizes: MutableMap<String, Long>
    ): MutableMap<String, Long> {
        tree.forEach { (_, value) ->
            value.forEach {
                when (it) {
                    is Directory -> {
                        path.add(it.getName())
                        sizes[path.joinToString("/")] = it.directorySize()
                        findDirSizes(path, it.items, sizes)
                        path.removeLast()
                    }

                    is File -> {}
                }
            }
        }
        return sizes
    }

    private fun buildTree(lines: List<String>): MutableMap<String, MutableList<Item>> {
        val currentPath: MutableList<String> = mutableListOf()
        val tree: MutableMap<String, MutableList<Item>> = mutableMapOf()
        var listing = false
        lines.forEach {
            val line = it.split(" ")
            if (line[0] == "$") { // command
                listing = false
                if (line[1] == "ls") {
                    listing = true
                } else if (line[1] == "cd") {
                    if (line[2] == "/") {
                        currentPath.clear()
                        currentPath += "/"
                    } else if (line[2] == "..")
                        currentPath.removeLast()
                    else {
                        currentPath += line[2]
                    }
                }
            } else if (listing) {
                val items: MutableList<Item> = findItem(currentPath, tree)
                if (line[0] == "dir") { // directory
                    items += Directory(line[1], mutableMapOf())
                } else { // file
                    items += File(line[1], line[0].toLong())
                }
            }
        }
        return tree
    }

    private fun findItem(path: List<String>, parent: MutableMap<String, MutableList<Item>>): MutableList<Item> {
        val current = parent[path.first()]
        return if (current == null) {
            parent[path.first()] = mutableListOf()
            parent[path.first()]!!
        } else if (path.size == 1) {
            current
        } else {
            val directory: Directory? = findDirectory(path, current)
            findItem(path.subList(1, path.size), retrieveItems(directory))
        }
    }

    private fun findDirectory(path: List<String>, current: MutableList<Item>): Directory? {
        val directory: Item? = current.find {
            it.getName() == path[1] && it is Directory
        }
        return if (directory != null) {
            directory as Directory
        } else {
            null
        }
    }

    private fun retrieveItems(directory: Directory?): MutableMap<String, MutableList<Item>> {
        return directory?.items ?: mutableMapOf()
    }
}

