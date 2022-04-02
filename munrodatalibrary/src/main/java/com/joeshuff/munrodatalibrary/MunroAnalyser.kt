package com.joeshuff.munrodatalibrary

class MunroAnalyser {

    private var loadedMunros = arrayListOf<Munro>()

    init {
        loadData()
    }

    private fun loadData(filename: String = "") {
        loadedMunros.clear()

        loadedMunros.apply {
            add(Munro("Test Munro", 3000f, HillCategory.MUNRO_TOP, "123456"))
            add(Munro("Test Munro 2", 1000f, HillCategory.MUNRO, "wertyui"))
            add(Munro("Test Munro 3", 2000f, HillCategory.MUNRO_TOP, "9876t5"))
        }
    }

    fun getMunros(): List<Munro> = loadedMunros

    fun getMunrosByType(searchHillCategory: HillCategory = HillCategory.EITHER): List<Munro> =
        Builder()
            .byType(searchHillCategory)
            .apply()

    fun getMunrosByHeight(sortDir: SortDirection): List<Munro> =
        Builder()
            .byHeight(sortDir)
            .apply()

    fun getMunrosByName(sortDir: SortDirection): List<Munro> =
        Builder()
            .byName(sortDir)
            .apply()

    class Builder {
        private val filterInstructions = arrayListOf<(List<Munro>) -> List<Munro>>()

        fun byType(category: HillCategory) = apply {
            filterInstructions.add { it.filter {
                if (category == HillCategory.EITHER) true
                else it.hillCategory == category
            } }
        }

        fun byHeight(sortDir: SortDirection) = apply {
            filterInstructions.add {
                when (sortDir) {
                    SortDirection.ASC -> it.sortedBy { it.heightMetric }
                    SortDirection.DESC -> it.sortedByDescending { it.heightMetric }
                }
            }
        }

        fun byName(sortDir: SortDirection) = apply {
            filterInstructions.add {
                when (sortDir) {
                    SortDirection.ASC -> it.sortedBy { it.name }
                    SortDirection.DESC -> it.sortedByDescending { it.name }
                }
            }
        }

        fun apply(): List<Munro> {
            var munros = MunroAnalyser().getMunros()

            filterInstructions.forEach {
                munros = it.invoke(munros)
            }

            return munros
        }
    }

}