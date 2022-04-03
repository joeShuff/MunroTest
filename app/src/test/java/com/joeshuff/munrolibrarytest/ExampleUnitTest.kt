package com.joeshuff.munrolibrarytest

import com.google.common.truth.Truth.assertThat
import com.joeshuff.munrodatalibrary.HillCategory
import com.joeshuff.munrodatalibrary.Munro
import com.joeshuff.munrodatalibrary.MunroAnalyser
import com.joeshuff.munrodatalibrary.SortDirection
import org.junit.Test

import org.junit.Before
import java.io.File

class MunroDataUnitTesting {

    lateinit var munroAnalyser: MunroAnalyser

    @Before
    fun setup() {
        val file = File("test.csv")
        munroAnalyser = MunroAnalyser()
    }

    @Test
    fun `data loaded correctly from csv`() {
        assertThat(munroAnalyser.getMunros().size).isEqualTo(602)
    }

    @Test
    fun `search munros by hill type munro`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.MUNRO)
                .apply()
                .size
        ).isEqualTo(284)
    }

    @Test
    fun `search munros by hill type munro top`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.MUNRO_TOP)
                .apply()
                .size
        ).isEqualTo(227)
    }

    @Test
    fun `search munros by hill type either returns all`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.EITHER)
                .apply()
                .size
        ).isEqualTo(602)
    }

    @Test
    fun `sort munros by height ascending`() {
        assertThat(
            MunroAnalyser.Builder()
                .orderByHeight(SortDirection.ASC)
                .apply()
        ).isInOrder(compareBy<Munro> { it.heightMetric })
    }

    @Test
    fun `sort munros by height descending`() {
        assertThat(
            MunroAnalyser.Builder()
                .orderByHeight(SortDirection.DESC)
                .apply()
        ).isInOrder(compareByDescending<Munro> { it.heightMetric })
    }

    @Test
    fun `sort munros by name ascending`() {
        assertThat(
            MunroAnalyser.Builder()
                .orderByName(SortDirection.ASC)
                .apply()
        ).isInOrder(compareBy<Munro> { it.name })
    }

    @Test
    fun `sort munros by name descending`() {
        assertThat(
            MunroAnalyser.Builder()
                .orderByName(SortDirection.DESC)
                .apply()
        ).isInOrder(compareByDescending<Munro> { it.name })
    }

    @Test
    fun `limit size of output list`() {
        assertThat(
            MunroAnalyser.Builder()
                .limit(10)
                .apply()
                .size
        ).isEqualTo(10)
    }

    @Test
    fun `min height set returns only larger munros`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByMinHeight(1000f)
                .apply()
                .size
        ).isEqualTo(259)
    }

    @Test
    fun `max height set returns only lower munros`() {
        val query = MunroAnalyser.Builder()
            .filterByMaxHeight(1000f)
            .apply()

        assertThat(query.size).isEqualTo(341)
    }

    @Test
    fun `top 10 munro tops that are over one thousand meters ordered by name ascending`() {
        val searchQuery = MunroAnalyser.Builder()
            .filterByType(HillCategory.MUNRO_TOP)
            .filterByMinHeight(1000f)
            .orderByName(SortDirection.ASC)
            .limit(10)
            .apply()

        assertThat(searchQuery.size).isEqualTo(10)
        assertThat(searchQuery.all { it.hillCategory == HillCategory.MUNRO_TOP }).isTrue()
        assertThat(searchQuery.all { it.heightMetric > 1000f }).isTrue()
    }

    @Test
    fun `empty query returns whole list`() {
        assertThat(MunroAnalyser.Builder().apply().size).isEqualTo(602)
    }

    @Test
    fun `order by name then height`() {
        val searchQuery = MunroAnalyser.Builder()
            .orderBy(compareBy({ it.name }, {it.heightMetric}))
            .apply()

        assertThat(searchQuery).isInOrder(compareBy<Munro>({ it.name }, {it.heightMetric}))
    }
}