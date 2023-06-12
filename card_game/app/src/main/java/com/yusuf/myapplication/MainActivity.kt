package com.yusuf.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.os.CountDownTimer
import android.widget.TextView
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var buttonIds= arrayOf(R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,
        R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12)

        var imageIds= arrayOf(R.drawable.gandalf,R.drawable.aragorn,R.drawable.boromir,R.drawable.elrond,R.drawable.eowyn,
        R.drawable.frodo,R.drawable.galadriel,R.drawable.gimli,R.drawable.sam,R.drawable.sauron,R.drawable.legolas,R.drawable.theoden)

        var randomImage=randomNumberList(6)
        var randomCard=randomNumberList(12)
        // MP3 dosyasını yükle
        mediaPlayer = MediaPlayer.create(this, R.raw.song2)

        // Çalmak için mediaPlayer'ı başlat
        mediaPlayer?.start()
        var sayac=0
        var dogru_sayac=0
        var yanlis_sayac=0
        val countdownDurationMillis: Long = 21000 // Geri sayım süresi (20 saniye = 20000 milisaniye)
        val countdownTimer = object : CountDownTimer(countdownDurationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                var sayac=findViewById<TextView>(R.id.textView)
                sayac.text=secondsRemaining.toString()

            }

            override fun onFinish() {
                for (id in buttonIds){
                    var myButton=findViewById<Button>(id)
                    myButton.isClickable=false
                }
                var score=dogru_sayac*10-yanlis_sayac*7
                var score_view=findViewById<TextView>(R.id.textView2)
                score_view.text="Skor $score"


            }
        }
        countdownTimer.start()

        var card1=findViewById<Button>(buttonIds[randomCard[0]]) ;card1.setText("0")
        var card2=findViewById<Button>(buttonIds[randomCard[1]]) ;card2.setText("0")
        var card3=findViewById<Button>(buttonIds[randomCard[2]]) ;card3.setText("1")
        var card4=findViewById<Button>(buttonIds[randomCard[3]]) ;card4.setText("1")
        var card5=findViewById<Button>(buttonIds[randomCard[4]]) ;card5.setText("2")
        var card6=findViewById<Button>(buttonIds[randomCard[5]]) ;card6.setText("2")
        var card7=findViewById<Button>(buttonIds[randomCard[6]]) ;card7.setText("3")
        var card8=findViewById<Button>(buttonIds[randomCard[7]]) ;card8.setText("3")
        var card9=findViewById<Button>(buttonIds[randomCard[8]]) ;card9.setText("4")
        var card10=findViewById<Button>(buttonIds[randomCard[9]]) ;card10.setText("4")
        var card11=findViewById<Button>(buttonIds[randomCard[10]]) ;card11.setText("5")
        var card12=findViewById<Button>(buttonIds[randomCard[11]]) ;card12.setText("5")
        var stack1=ArrayList<Int>()
        var stack2=ArrayList<Int>()
        var long_stack=ArrayList<Int>()

        for (id in buttonIds){
            val myButton=findViewById<Button>(id)
            myButton.setOnClickListener({
                var get_id=myButton.text.toString().toInt()
                myButton.setBackgroundResource(imageIds[randomImage[get_id]])
                if (stack1.isEmpty()){
                    stack1.add(get_id)
                    stack2.add(id)
                    long_stack.add(id)
                    myButton.setBackgroundResource(imageIds[randomImage[get_id]])
                }
                else if(stack1[0]==get_id && stack2[0]!=id){
                    dogru_sayac=dogru_sayac+1
                    var card=findViewById<Button>(stack2[0])
                    myButton.setBackgroundResource(imageIds[randomImage[get_id]])
                    long_stack.add(id)
                    for (num in long_stack){
                        var unclickable_button=findViewById<Button>(num)
                        unclickable_button.isClickable=false
                    }
                    stack1.clear()
                    stack2.clear()
                    if (dogru_sayac==6){
                        countdownTimer.cancel()
                        println("oyun bitti")
                        println(yanlis_sayac)
                        mediaPlayer2 = MediaPlayer.create(this, R.raw.win)
                        mediaPlayer2?.start()
                        var sure=findViewById<TextView>(R.id.textView).text.toString().toInt()
                        var score=dogru_sayac*10*sure-yanlis_sayac*7
                        var score_view=findViewById<TextView>(R.id.textView2)
                        score_view.text="Skor $score"
                    }
                    else{
                        mediaPlayer1 = MediaPlayer.create(this, R.raw.true_song)
                        mediaPlayer1?.start()
                    }
                }
                else if(stack1[0]!=get_id){
                    println("yanlis cevap")
                    yanlis_sayac=yanlis_sayac+1
                    var card=findViewById<Button>(stack2[0])
                    long_stack.removeAt(long_stack.size-1)
                    println(long_stack)
                    Handler().postDelayed({
                        card.setBackgroundResource(R.drawable.backcard)
                        myButton.setBackgroundResource(R.drawable.backcard)
                    },300L)
                    for (num in long_stack){
                        var unclickable_button=findViewById<Button>(num)
                        unclickable_button.isClickable=false
                    }
                    stack1.clear()
                    stack2.clear()
                }
                else{
                    if (sayac<=2){
                        sayac=sayac+1
                        myButton.setBackgroundResource(R.drawable.backcard)
                        long_stack.removeAt(long_stack.size-1)
                        stack1.clear()
                        stack2.clear()
                    }
                }
            })
        }
    }
    fun randomNumberList(Size:Int) :MutableList<Int>{
        val random = java.util.Random()
        val listSize = Size
        val numberList = mutableListOf<Int>()
        while (numberList.size < listSize) {
            val randomNumber = random.nextInt(12) // 0 ile 11 arasında rastgele bir sayı üretir

            if (!numberList.contains(randomNumber)) {
                numberList.add(randomNumber)
            }
        }

        return numberList
    }
    override fun onDestroy() {
        super.onDestroy()
        // Aktivite kapatıldığında mediaPlayer'ı serbest bırak
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer1?.release()
        mediaPlayer = null
        mediaPlayer2?.release()
        mediaPlayer = null
    }
}