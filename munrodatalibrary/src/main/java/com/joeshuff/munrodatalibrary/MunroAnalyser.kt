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
        loadedMunros.filter {
            if (searchHillCategory == HillCategory.EITHER) true
            else it.hillCategory == searchHillCategory
        }

    fun getMunrosByHeight(sortDir: SortDirection): List<Munro> {
        return when (sortDir) {
            SortDirection.ASC -> loadedMunros.sortedBy { it.heightMetric }
            SortDirection.DESC -> loadedMunros.sortedByDescending { it.heightMetric }
        }
    }

    fun getMunrosByName(sortDir: SortDirection): List<Munro> {
        return when (sortDir) {
            SortDirection.ASC -> loadedMunros.sortedBy { it.name }
            SortDirection.DESC -> loadedMunros.sortedByDescending { it.name }
        }
    }
}