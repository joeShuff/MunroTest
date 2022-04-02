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
        assertThat(munroAnalyser.getMunros().size).isEqualTo(80)
    }

    @Test
    fun `search munros by hill type munro`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.MUNRO)
                .apply()
                .size
        ).isEqualTo(40)
    }

    @Test
    fun `search munros by hill type munro top`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.MUNRO_TOP)
                .apply()
                .size
        ).isEqualTo(40)
    }

    @Test
    fun `search munros by hill type either returns all`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByType(HillCategory.EITHER)
                .apply()
                .size
        ).isEqualTo(80)
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
        ).isEqualTo(58)
    }

    @Test
    fun `max height set returns only lower munros`() {
        assertThat(
            MunroAnalyser.Builder()
                .filterByMaxHeight(1000f)
                .apply()
                .size
        ).isEqualTo(20)
    }

    @Test
    fun `top 10 munro tops that are over twelve hundred meters ordered by name ascending`() {
        val searchQuery = MunroAnalyser.Builder()
            .filterByType(HillCategory.MUNRO_TOP)
            .filterByMinHeight(1200f)
            .orderByName(SortDirection.ASC)
            .limit(10)
            .apply()

        assertThat(searchQuery.size).isEqualTo(10)
        assertThat(searchQuery.filter { it.hillCategory != HillCategory.MUNRO_TOP }).isEmpty()
        assertThat(searchQuery.filter { it.heightMetric < 1200f }).isEmpty()
    }

    @Test
    fun `empty query returns whole list`() {
        assertThat(MunroAnalyser.Builder().apply().size).isEqualTo(80)
    }

    @Test
    fun `order by name then height`() {

    }
}