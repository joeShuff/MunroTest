package com.joeshuff.munrodatalibrary

import java.io.File
import kotlin.math.min


class MunroAnalyser {

    private var loadedMunros = arrayListOf<Munro>()

    init {
        loadData("munrotab_v6.2.csv")
    }

    private fun loadData(filename: String = "") {
        loadedMunros.clear()

        val lines = File(filename).readLines()

        lines.subList(1, lines.size).forEach {
            val asList = it.split(",")

            Munro.loadFromCsvEntryOrNull(asList)?.let {
                loadedMunros.add(it)
            }
        }
    }

    fun getMunros(): List<Munro> = loadedMunros

    class Builder {
        private val filterInstructions = arrayListOf<(List<Munro>) -> List<Munro>>()

        private var sizeLimit: Int? = null

        fun filterByType(category: HillCategory) = apply {
            filterInstructions.add { it.filter {
                if (category == HillCategory.EITHER) true
                else it.hillCategory == category
            } }
        }

        fun filterByMinHeight(minimumHeight: Float) = apply {
            filterInstructions.add {
                it.filter { it.heightMetric > minimumHeight }
            }
        }

        fun filterByMaxHeight(maximumHeight: Float) = apply {
            filterInstructions.add {
                it.filter { it.heightMetric < maximumHeight }
            }
        }

        fun orderByHeight(sortDir: SortDirection) = apply {
            filterInstructions.add {
                when (sortDir) {
                    SortDirection.ASC -> it.sortedBy { it.heightMetric }
                    SortDirection.DESC -> it.sortedByDescending { it.heightMetric }
                }
            }
        }

        fun orderByName(sortDir: SortDirection = SortDirection.ASC) = apply {
            filterInstructions.add {
                when (sortDir) {
                    SortDirection.ASC -> it.sortedBy { it.name }
                    SortDirection.DESC -> it.sortedByDescending { it.name }
                }
            }
        }

        fun orderBy(munroComparator: Comparator<Munro>) = apply {
            filterInstructions.add {
                it.sortedWith(munroComparator)
            }
        }

        fun limit(maxSize: Int) = apply { sizeLimit = maxSize }

        fun apply(): List<Munro> {
            var munros = MunroAnalyser().getMunros()

            filterInstructions.forEach {
                munros = it.invoke(munros)
            }

            return munros.subList(0, min(munros.size, sizeLimit?: munros.size))
        }
    }

}