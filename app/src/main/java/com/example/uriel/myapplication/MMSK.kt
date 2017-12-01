package com.example.uriel.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_mmsk.*

class MMSK : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mmsk)
        title = "M/M/S/K"
        //supportActionBar?.subtitle = "S canales de servicio"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.calcular -> {
                if(editText3.text.isNotEmpty() && editText5.text.isNotEmpty() && editText4.text.isNotEmpty() && editText6.text.isNotEmpty()){
                    val siguiente = Intent(this, RESPUESTAS::class.java)
                    siguiente.putExtra("tipo",6)
                    siguiente.putExtra("valores", doubleArrayOf((editText3.text).toString().toDouble(),(editText5.text).toString().toDouble(), editText4.text.toString().toDouble(), editText6.text.toString().toDouble()))
                    startActivity(siguiente)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
