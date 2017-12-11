package com.example.uriel.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_respuestas.*
import java.io.FileInputStream

class RESPUESTAS : AppCompatActivity() {
    var pn = 5
    var mensaje = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuestas)

        title = "Resultados"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fos: FileInputStream?
        var palabra = ""
        try{
            fos = openFileInput("decimales")
            for (letra in fos.readBytes()) palabra += letra.toChar()
            fos?.close()
        }catch (e: Exception){
            println(e.stackTrace)
        }
        var long = 5
        if (palabra.matches("-?\\d+(\\.\\d+)?".toRegex())){
            long = palabra.toInt()
            pn = long
        }

        val tipo = intent.getIntExtra("tipo",0)
        if (tipo == 3){
            supportActionBar?.subtitle = "M/M/1/C"
            mensaje = "λ = Tasa de llegadas\nμ = Tasa de servicio\nC = Tamaño de la población\n" +
                    "Ls = Número esperado de unidades en el sistema\nLq = Número esperado de unidades en la cola\n" +
                    "Ws = Tiempo medio de espera en el sistema\n Wq = Tiempo medio de espera en la cola\n" +
                    "ρ = Probabilidad de encontrar el sistema ocupado\nP0 = Encontrar el sistema vacio\n" +
                    "Pn = Encontrar n unidades en el sistema\n"

            val valores = intent.getDoubleArrayExtra("valores")
            val lambda = valores[0]
            val mu = valores[1]
            val m=valores[2]
            //val long = 5
            var sumatoria = 0.0
            for (i in 0 .. m.toInt()){
                sumatoria += (factorial(m.toLong())/factorial((m-i).toLong()))*(Math.pow(lambda/mu,i.toDouble()))
            }
            val P0 = 1.0/sumatoria
            val L = m-(mu/lambda)*(1-P0)
            val Lq = m-((lambda+mu)/lambda)*(1-P0)
            val Wq = Lq/(mu*(1-P0))
            val W = Wq + (1/mu)
            val p = lambda/mu
            textView19.visibility = View.GONE
            space19.visibility = View.GONE
            textView20.visibility = View.GONE
            space20.visibility = View.GONE

            textView15.text = "λ = ${formateador(lambda,long)}, μ = ${formateador(mu,long)} y C = ${m.toInt()}"
            textView17.text = "Ls = m-(μ/λ)(1-P0) = ${formateador(L,long)}"
            textView18.text = "Lq = m-(λ+μ/λ)(1-P0) = ${formateador(Lq,long)}"
            //textView19.text = "Lo = Sρ = ${formateador(s,long)}*${formateador((lambda/(mu*s)),long)} = ${formateador(s*(lambda/(mu*s)),long)}"
            //textView20.text = "LD = S-Lo = ${formateador(s,long)}-${formateador(s*(lambda/(mu*s)),long)} = ${formateador(s-(s*(lambda/(mu*s))),long)}"
            textView22.text = "Ws = Wq+(1/μ) = ${formateador(W,long)}"
            textView23.text = "Wq = Lq/μ(1-P0) = ${formateador(Wq,long)}"
            textView25.text = "ρ = λ/μ = ${formateador(p,long)}"

            var acumulador: Double
            var cadena = "P0 = 1/Σ[(m!/(m-n)!)(λ/μ)^n] = ${formateador(P0,long)}\n"
            acumulador = P0
            cadena += "acumulado, P0 = ${formateador(acumulador, long)}\n\n"
            var n = 1.0
            //var detener = 1.0
            while(acumulador<1.0 && n<=m){// quite el " && detener >= 0.00001" del while
                cadena += "P${n.toInt()} = [m!/(m-n)!][(λ/μ)^m](P0) = ${formateador((factorial(m.toLong())/factorial((m-n).toLong()))*Math.pow(lambda/mu,m)*(P0))}\n"
                acumulador += ((factorial(m.toLong())/factorial((m-n).toLong()))*Math.pow(lambda/mu,m)*(P0))
                //detener = ((factorial(m.toLong())/factorial((m-n).toLong()))*Math.pow(lambda/mu,m)*(P0))

                cadena += "acumulado, p${n.toInt()} = ${formateador(acumulador)}\n\n"
                n++
            }
            textView26.text = cadena

        }
        else if(tipo == 4){
            supportActionBar?.subtitle = "M/M/S/C"
            mensaje = "λ = Tasa de llegadas\nμ = Tasa de servicio\n" +
                    "S = Número de canales de servicio\nC = Tamaño de la población\n" +
                    "Ls = Número esperado de unidades en el sistema\nLq = Número esperado de unidades en la cola\n" +
                    "Ws = Tiempo medio de espera en el sistema\n Wq = Tiempo medio de espera en la cola\n" +
                    "ρ = Probabilidad de encontrar el sistema ocupado\nP0 = Encontrar el sistema vacio\n" +
                    "Pn = Encontrar n unidades en el sistema\n"

            val valores = intent.getDoubleArrayExtra("valores")
            val lambda = valores[0]
            val mu = valores[1]
            val s = valores[2]
            val m = valores[3]
            //val long = 5
            var sumatoria = 0.0
            var sumatoria2 = 0.0
            for (i in 0 until s.toInt()){
                sumatoria += (factorial(m.toLong())/(factorial((m-i).toLong())*factorial(i.toLong())))*(Math.pow(lambda/mu,i.toDouble()))
            }
            for (i in s.toInt() .. m.toInt()){
                sumatoria2 += (factorial(m.toLong())/(factorial((m-i).toLong())*factorial(s.toLong())*Math.pow(s,i-s)))*Math.pow(lambda/mu,i.toDouble())
            }
            val P0 = 1.0/(sumatoria+sumatoria2)
            val p = lambda/(mu*s)
            textView19.visibility = View.GONE
            space19.visibility = View.GONE
            textView20.visibility = View.GONE
            space20.visibility = View.GONE
            textView15.text = "λ = ${formateador(lambda,long)}, μ = ${formateador(mu,long)}, S = ${s.toInt()} y C = ${m.toInt()}"
            textView25.text = "ρ = λ/μS = ${formateador(p,long)}"

            var acumulador: Double
            var cadena = "P0 = 1/Σ[(m!/(m-n)!n!)(λ/μ)^n]+Σ[(m!/(m-n)!S!S^(n-S))(λ/μ)^n] = ${formateador(P0,long)}\n"
            acumulador = P0
            cadena += "acumulado, P0 = ${formateador(acumulador, long)}\n\n"
            var n = 1.0
            //var detener = 1.0
            while(n<=m){// quite el " && detener >= 0.00001" del while
                if (n<s){
                    cadena += "P${n.toInt()} = [(m!/(m-n)!n!)(λ/μ)^n](P0) = ${formateador(((factorial(m.toLong())/(factorial((m-n).toLong())*factorial(n.toLong())))*(Math.pow(lambda/mu,n)))*(P0))}\n"
                    acumulador += ((factorial(m.toLong())/(factorial((m-n).toLong())*factorial(n.toLong())))*(Math.pow(lambda/mu,n)))*(P0)
                }
                else if (n>=s){
                    cadena += "P${n.toInt()} = [(m!/(m-n)!S!S^(n-S))(λ/μ)^n](P0) = ${formateador(((factorial(m.toLong())/(factorial((m-n).toLong())*factorial(s.toLong())*Math.pow(s,n-s)))*Math.pow(lambda/mu,n))*(P0))}\n"
                    acumulador += ((factorial(m.toLong())/(factorial((m-n).toLong())*factorial(s.toLong())*Math.pow(s,n-s)))*Math.pow(lambda/mu,n))*(P0)
                }

                //detener = ((factorial(m.toLong())/factorial((m-n).toLong()))*Math.pow(lambda/mu,m)*(P0))
                cadena += "acumulado, p${n.toInt()} = ${formateador(acumulador)}\n\n"
                n++
            }
            textView26.text = cadena

        }
        else if(tipo == 5){
            supportActionBar?.subtitle = "M/M/1/K"

            val valores = intent.getDoubleArrayExtra("valores")
            val lambda = valores[0]
            val mu = valores[1]
            val k=valores[2]
            textView15.text = "λ = ${formateador(lambda,long)}, μ = ${formateador(mu,long)}, K = ${k.toInt()} y λ̅  = "
            //

        }
        else if(tipo == 6){
            supportActionBar?.subtitle = "M/M/S/K"

            val valores = intent.getDoubleArrayExtra("valores")
            val lambda = valores[0]
            val mu = valores[1]
            val s = valores[2]
            val k=valores[3]
            textView15.text = "λ = ${formateador(lambda,long)}, μ = ${formateador(mu,long)}, S = ${s.toInt()} y K = ${k.toInt()}"
            //Probabilidades
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun formateador(string:Double, longitud:Int = pn):String{
        when {
            string.isInfinite() -> return "∞"
            string%1 == 0.0 -> return "${string.toInt()}"
            string.toString().length>longitud+2 -> return String.format("%.${longitud}f",string)
            else -> return string.toString()
        }

    }



    tailrec fun factorial(n: Long, multiplicatoria: Long = 1): Long {
        val respuesta:Long
        respuesta = if (n!=0L) n*multiplicatoria else 1*multiplicatoria
        return if (n <= 1) {
            respuesta
        } else {
            factorial(n - 1, respuesta)
        }
    }

    private fun showSimpleAlert() {

        val simpleAlert = AlertDialog.Builder(this@RESPUESTAS).create()
        simpleAlert.setTitle("Terminos")
        simpleAlert.setMessage("$mensaje")

        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            dialogInterface, i ->
            Toast.makeText(applicationContext, "Cerró la información", Toast.LENGTH_SHORT).show()
        })

        simpleAlert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.help, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_help -> {
                showSimpleAlert()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
