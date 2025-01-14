package com.example.cargame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.color.utilities.Score

class MainActivity : AppCompatActivity(),GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn :Button
    lateinit var mGameView : GameView
    lateinit var score: TextView
    lateinit var highScoreTextView: TextView
    var highScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout=findViewById(R.id.rootLayout)
        score=findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore)
        highScore = loadHighScore()
        highScoreTextView.text = "High Score : $highScore"
        mGameView=GameView(this,this)

        startBtn.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameView)
            startBtn.visibility=View.GONE
            score.visibility=View.GONE
            highScoreTextView.visibility=View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        if(mScore>highScore){
            highScore=mScore
            highScoreTextView.text = "High Score : $highScore"
            saveHighScore(highScore)
        }
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility=View.VISIBLE
        score.visibility=View.VISIBLE
        highScoreTextView.visibility=View.VISIBLE
    }

    private fun saveHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("HighScore", score)
        editor.apply()
    }

    private fun loadHighScore(): Int {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("HighScore", 0)
    }
}