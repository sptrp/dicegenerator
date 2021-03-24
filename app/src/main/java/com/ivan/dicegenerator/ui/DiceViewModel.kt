package com.ivan.dicegenerator.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivan.dicegenerator.R
import com.ivan.dicegenerator.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DiceViewModel @Inject constructor() : ViewModel() {

    private val _rolledDice = MutableLiveData(intArrayOf(6, 6, 6, 6, 6))
    val rolledDice: MutableLiveData<IntArray> = _rolledDice

    private val _gameStarted = SingleLiveEvent<Boolean>()
    val gameStarted: LiveData<Boolean> = _gameStarted

    private fun getDie(): Int {
        return Random.nextInt(1, 7)
    }

    fun rollDice() {
        _rolledDice.value = intArrayOf(
            getDie(),
            getDie(),
            getDie(),
            getDie(),
            getDie()
        )
        _gameStarted.value = true
    }

    // Evaluate the results of a dice roll
    fun evaluateDice(context: Context, dice: IntArray?): String {

        // Initialize a map of die counts
        val result = mutableMapOf(
            Pair(1, 0),
            Pair(2, 0),
            Pair(3, 0),
            Pair(4, 0),
            Pair(5, 0),
            Pair(6, 0)
        )

        // Update the die counts for each of 5 dice
        for (i in dice!!.indices) {
            val currentCount = result.getOrElse(dice[i]) { 0 }
            result[dice[i]] = currentCount + 1
        }

        return when {
            result.containsValue(5) ->
                context.getString(R.string.five_of_a_kind)
            result.containsValue(4) ->
                context.getString(R.string.four_of_a_kind)
            isFullHouse(result) ->
                context.getString(R.string.full_house)
            isStraight(dice) ->
                context.getString(R.string.straight)
            result.containsValue(3) ->
                context.getString(R.string.three_of_a_kind)
            is2Pairs(result.values) ->
                context.getString(R.string.two_pairs)
            result.containsValue(2) ->
                context.getString(R.string.pair)

            else ->
                context.getString(R.string.nothing_special)
        }
    }

    private fun isFullHouse(result: MutableMap<Int, Int>): Boolean {
        return result.containsValue(3) && result.containsValue(2)
    }

    private fun is2Pairs(values: MutableCollection<Int>): Boolean {
        var foundPair = false

        for (value in values) {
            if (value == 2) {
                if (foundPair) return true else foundPair = true
            }
        }
        return false
    }

    // Check for straight
    private fun isStraight(dice: IntArray): Boolean {
        return (dice.contains(1) || dice.contains(6)) &&
                dice.contains(2) &&
                dice.contains(3) &&
                dice.contains(4) &&
                dice.contains(5)
    }

}