package com.joeshuff.munrodatalibrary

enum class HillCategory {
    MUNRO, MUNRO_TOP, EITHER
}

data class Munro(
    val name: String,
    val heightMetric: Float,
    val hillCategory: HillCategory,
    val gridRef: String
)