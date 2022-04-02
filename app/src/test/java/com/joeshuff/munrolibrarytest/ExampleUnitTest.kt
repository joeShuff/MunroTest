package com.joeshuff.munrolibrarytest

import com.google.common.truth.Truth.assertThat
import com.joeshuff.munrodatalibrary.HillCategory
import com.joeshuff.munrodatalibrary.Munro
import com.joeshuff.munrodatalibrary.MunroAnalyser
import com.joeshuff.munrodatalibrary.SortDirection
import org.junit.Test

import org.junit.Before

class MunroDataUnitTesting {

    lateinit var munroAnalyser: MunroAnalyser

    @Before
    fun setup() {
        munroAnalyser = MunroAnalyser()
    }

    @Test
    fun `data loaded correctly from csv`() {
        assertThat(munroAnalyser.getMunros().size).isEqualTo(3)
    }

    @Test
    fun `search munros by hill type munro`() {
        assertThat(munroAnalyser.getMunrosByType(HillCategory.MUNRO).size).isEqualTo(1)
    }

    @Test
    fun `search munros by hill type munro top`() {
        assertThat(munroAnalyser.getMunrosByType(HillCategory.MUNRO_TOP).size).isEqualTo(2)
    }

    @Test
    fun `search munros by hill type either returns all`() {
        assertThat(munroAnalyser.getMunrosByType(HillCategory.EITHER).size).isEqualTo(3)
    }

    @Test
    fun `sort munros by height ascending`() {
        assertThat(munroAnalyser.getMunrosByHeight(SortDirection.ASC))
            .isInOrder(compareBy<Munro> { it.heightMetric })
    }

    @Test
    fun `sort munros by height descending`() {
        assertThat(munroAnalyser.getMunrosByHeight(SortDirection.DESC))
            .isInOrder(compareByDescending<Munro> { it.heightMetric })
    }

    @Test
    fun `sort munros by name ascending`() {
        assertThat(munroAnalyser.getMunrosByName(SortDirection.ASC))
            .isInOrder(compareBy<Munro> { it.name })
    }
}