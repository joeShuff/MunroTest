package com.joeshuff.munrodatalibrary

import java.lang.Exception

enum class HillCategory {
    MUNRO, MUNRO_TOP, EITHER, NONE;

    companion object {
        fun fromString(stringValue: String): HillCategory {
            return when (stringValue) {
                "MUN" -> MUNRO
                "TOP" -> MUNRO_TOP
                else -> NONE
            }
        }
    }
}

data class Munro(
    val name: String,
    val heightMetric: Float,
    val hillCategory: HillCategory,
    val gridRef: String
) {
    companion object {
        fun loadFromCsvEntryOrNull(csvData: List<String>): Munro? {
            return try {
                Munro(
                    csvData[6],
                    csvData[10].toFloat(),
                    HillCategory.fromString(csvData[27]),
                    csvData[14]
                )
            } catch (e: Exception) { null }
        }
    }
}