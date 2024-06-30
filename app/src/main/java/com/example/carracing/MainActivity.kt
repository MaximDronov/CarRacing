package com.example.carracing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCarCount = findViewById<EditText>(R.id.etCarCount)
        val btnStartRace = findViewById<Button>(R.id.btnStartRace)

        btnStartRace.setOnClickListener {
            val carCount = etCarCount.text.toString().toIntOrNull() ?: return@setOnClickListener
            val cars = createCars(carCount)
            val winner = startRaces(cars)
            Log.d("Race", "Победитель: Машина ${winner.id}")
        }
    }

    private fun createCars(count: Int): List<Car> {
        return List(count) { Car(it + 1) }
    }

    private fun startRaces(cars: List<Car>): Car {
        var remainingCars = cars.toMutableList()

        while (remainingCars.size > 1) {
            remainingCars = raceRound(remainingCars)
        }

        return remainingCars.first()
    }

    private fun raceRound(cars: MutableList<Car>): MutableList<Car> {
        val winners = mutableListOf<Car>()
        cars.shuffle()

        for (i in cars.indices step 2) {
            if (i + 1 < cars.size) {
                val car1 = cars[i]
                val car2 = cars[i + 1]
                val winner = if (car1.speed > car2.speed) car1 else car2
                winners.add(winner)
                Log.d("Race", "Гонка между Машина ${car1.id} и Машина ${car2.id}, Победитель Машина ${winner.id}")
            } else {
                winners.add(cars[i])
                Log.d("Race", "Машина ${cars[i].id} - Нет пары, следующий круг")
            }
        }

        return winners
    }
}
