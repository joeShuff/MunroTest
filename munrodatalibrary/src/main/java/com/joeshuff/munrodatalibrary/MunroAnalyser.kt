package com.joeshuff.munrodatalibrary

import kotlin.math.max

class MunroAnalyser {

    private var loadedMunros = arrayListOf<Munro>()

    init {
        loadData()
    }

    private fun loadData(filename: String = "") {
        loadedMunros.clear()

        loadedMunros.apply {
            repeat(40) {
                add(Munro("Test MunroTop $it", it * 100f, HillCategory.MUNRO_TOP, "${it}topde"))
                add(Munro("Test Munro $it", it * 100f, HillCategory.MUNRO, "${it}abcde"))
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

        fun orderByHeight(sortDir: SortDirection) = apply {
            filterInstructions.add {
                when (sortDir) {
                    SortDirection.ASC -> it.sortedBy { it.heightMetric }
                    SortDirection.DESC -> it.sortedByDescending { it.heightMetric }
                }
            }
        }

        fun minHeight(minimumHeight: Float) = apply {
            filterInstructions.add {
                it.filter { it.heightMetric > minimumHeight }
            }
        }

        fun maxHeight(maximumHeight: Float) = apply {
            filterInstructions.add {
                it.filter { it.heightMetric < maximumHeight }
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

        fun limit(maxSize: Int) = apply { sizeLimit = maxSize }

        fun apply(): List<Munro> {
            var munros = MunroAnalyser().getMunros()

            filterInstructions.forEach {
                munros = it.invoke(munros)
            }

            return munros.subList(0, sizeLimit?: munros.size)
        }
    }

}