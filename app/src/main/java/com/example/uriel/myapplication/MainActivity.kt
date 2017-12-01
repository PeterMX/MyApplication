package com.example.uriel.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = "M/M/1"
        supportActionBar?.subtitle = "Un canal de servicio"
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
                if(editText.text.toString().isNotEmpty() && editText2.text.toString().isNotEmpty()){
                    try {
                        val siguiente: Intent = Intent(this, Main2Activity::class.java)
                        siguiente.putExtra("valores", doubleArrayOf((editText.text).toString().toDouble(),(editText2.text).toString().toDouble()))
                        startActivity(siguiente)
                    }finally {
                        println("Algo salio mal ")
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
