package com.example.uriel.myapplication

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP){
            button3.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/1</font></p><font size=\"18\" color=\"#4E7367\">Población infinita, capacidad del sistema infinito, un canal de servicio.</font>")
            button4.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/S</font></p><font size=\"18\" color=\"#4E7367\">Población infinita, capacidad del sistema infinito, S canales de servicio.</font>")
            button5.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/1/C</font></p><font size=\"18\" color=\"#4E7367\">Población de tamaño C, capacidad del sistema infinito, un canal de servicio.</font>")
            button6.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/S/C</font></p><font size=\"18\" color=\"#4E7367\">Población de tamaño C, capacidad del sistema infinito, S canales de servicio.</font>")
            button7.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/1/K</font></p><font size=\"18\" color=\"#4E7367\">Población infinita, capacidad del sistema limitado a K, un canal de servicio.</font>")
            button8.text = Html.fromHtml("<p style=\"font-size:46\"><font color=\"#22424B\">M/M/S/K</font></p><font size=\"18\" color=\"#4E7367\">Población infinita, capacidad del sistema limitado a K, S canales de servicio.</font>")
        }

        button3.setOnClickListener({
            val siguiente: Intent = Intent(this, MainActivity::class.java)
            startActivity(siguiente)
        })

        button4.setOnClickListener({
            val siguiente: Intent = Intent(this, Mms::class.java)
            startActivity(siguiente)
        })

        button5.setOnClickListener({
            val siguiente: Intent = Intent(this, MM1C::class.java)
            startActivity(siguiente)
        })

        button6.setOnClickListener{
            val siguiente: Intent = Intent(this, MMSC::class.java)
            startActivity(siguiente)
        }

        button7.setOnClickListener {
            val siguiente: Intent = Intent(this, MM1K::class.java)
            startActivity(siguiente)
        }

        button8.setOnClickListener {
            val siguiente: Intent = Intent(this, MMSK::class.java)
            startActivity(siguiente)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
