package com.ivan.dicegenerator.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.ivan.dicegenerator.MyLiveCycleObserver
import com.ivan.dicegenerator.R
import com.ivan.dicegenerator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private val diceViewModel: DiceViewModel by viewModels()

    private val headline by lazy { findViewById<TextView>(R.id.headline) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainBinding.lifecycleOwner = this
        setContentView(mainBinding.root)

        setSupportActionBar(toolbar)

        mainBinding.fab.setOnClickListener { view -> fabClickHandler(view) }

        lifecycle.addObserver(MyLiveCycleObserver())

        observeGameStatus()

        setDiceImageViews()
    }

    private fun fabClickHandler(view: View) {
        diceViewModel.rollDice()
        setDiceImageViews()
    }


    private fun setDiceImageViews() {
        val dice = diceViewModel.rolledDice.value
        var pointer = 0

        for (view in mainBinding.content.constraintLayout) {
            (view as? ImageView)?.let{

                val drawableId = when (dice?.get(pointer)) {
                    1 -> R.drawable.die_1
                    2 -> R.drawable.die_2
                    3 -> R.drawable.die_3
                    4 -> R.drawable.die_4
                    5 -> R.drawable.die_5
                    6 -> R.drawable.die_6
                    else -> R.drawable.die_6
                }

                view.setImageResource(drawableId)
                pointer++
            }
        }
        if (diceViewModel.gameStarted.value == true)
            headline.text = diceViewModel.evaluateDice(this, dice)
    }

    private fun observeGameStatus() {
        diceViewModel.gameStarted.observe(this, {
            if (it != false) return@observe
            diceViewModel.rolledDice.value
        })
    }

}
