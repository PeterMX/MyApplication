package com.example.uriel.myapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Context.MODE_PRIVATE
import kotlinx.android.synthetic.main.activity_configurar.*
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.regex.Pattern.matches


class configurar : AppCompatActivity() {

    val FILENAME = "decimales"
    //val string = "5"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configurar)
        title = "Configuraciones"

        //conf test code
        val fos:FileInputStream?
        var printsomthin:String? = ""
        try{
            fos = openFileInput(FILENAME)
            var palabra = ""
            for (letra in fos.readBytes()) palabra += letra.toChar()
            printsomthin = palabra
            fos?.close()
        }catch (e: Exception){
            println(e.stackTrace)
            /*when(e){
                is FileNotFoundException -> println(e.stackTrace)
                is StringIndexOutOfBoundsException -> println(e.stackTrace)
                else -> println(e.stackTrace)
            }*/
        }
        editTextDecimal.setText(printsomthin)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gonfig, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.guardar -> {
                //add code here
                val fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)
                when{
                    editTextDecimal.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex()) -> fos.write(editTextDecimal.text.toString().toByteArray())
                    else -> fos.write("5".toByteArray())
                }
                fos.close()
                return true
            }
            R.id.cancelar -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
