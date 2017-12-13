package com.example.uriel.myapplication

//import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_respuestas_mms.*
import java.io.FileInputStream

class RespuestasMms : AppCompatActivity() {
    var mensaje = ""
    //@SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuestas_mms)

        title = "Resultados"
        supportActionBar?.subtitle = "M/M/S"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mensaje = "λ = Tasa de llegadas\nμ = Tasa de servicio\nS = Número de canales de servicio\n" +
                "Ls = Número esperado de unidades en el sistema\nLq = Número esperado de unidades en la cola\n" +
                "Lo = Estaciones ocupadas\nLD = Estaciones desocupadas\n" +
                "Ws = Tiempo medio de espera en el sistema\n Wq = Tiempo medio de espera en la cola\n" +
                "ρ = Probabilidad de encontrar el sistema ocupado\nP0 = Encontrar el sistema vacio\n" +
                "Pn = Encontrar n unidades en el sistema\n"

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
        }
        val valores = intent.getDoubleArrayExtra("valores")
        val lambda = valores[0]
        val mu = valores[1]
        val s=valores[2]
        //val long = 5
        var sumatoria = 0.0
        for(n in 0..s.toInt()-1){
            sumatoria += (Math.pow((lambda/mu),n.toDouble())/factorial(n.toLong()))
            //println(Float.MAX_VALUE)
        }

        val p0:Double = (1/(sumatoria+(Math.pow((lambda/mu),s.toDouble())/(factorial(s.toLong())*(1-(lambda/(mu*s)))))))
        val Lq = (((Math.pow((lambda/mu).toDouble(),s.toDouble()))*(p0*(lambda/(mu*s))))/(factorial(s.toLong())*(Math.pow((1-(lambda/(mu*s))).toDouble(),2.0))))
        val L = (Lq+(s*(lambda/(mu*s))))

        textView15.text = "λ = ${formateador(lambda,long)}, μ = ${formateador(mu,long)} y S = ${s.toInt()}"
        textView17.text = "Ls = Lq+Lo = ${formateador(Lq,long)}+${formateador(s*(lambda/(mu*s)),long)} = ${formateador(L,long)}"
        textView18.text = "Lq = {[(λ/μ)^S(P0ρ)]/[S!(1-ρ)^2]} = ${formateador(Lq,long)}"
        textView19.text = "Lo = Sρ = ${formateador(s,long)}*${formateador((lambda/(mu*s)),long)} = ${formateador(s*(lambda/(mu*s)),long)}"
        textView20.text = "LD = S-Lo = ${formateador(s,long)}-${formateador(s*(lambda/(mu*s)),long)} = ${formateador(s-(s*(lambda/(mu*s))),long)}"
        textView22.text = "Ws = Wq+(1/μ) = Ls/λ = ${formateador(L,long)}/${formateador(lambda,long)} = ${formateador((L/lambda),long)}"
        textView23.text = "Wq = Lq/λ = ${formateador(Lq,long)}/${formateador(lambda,long)} = ${formateador((Lq/lambda),long)}"
        textView25.text = "ρ = λ/(μS) = ${formateador(lambda,long)}/${formateador(mu*s,long)} = ${formateador((lambda/(mu*s)),long)}"

        var acumulador: Double
        var cadena = "P0 = 1/[(Σ(λ/μ)^n/n!)+((λ/μ)^S/S!(1-ρ))] = ${formateador(p0,long)}\n"
        acumulador = p0
        cadena += "acumulado, p0 = ${formateador(acumulador, long)}\n\n"
        var n = 1.0
        var detener = 1.0
        while(acumulador<1.0 && n<=100.0 && detener >= 0.00001){
            //println("acumulador=$acumulador n=$n")
            if(n>s){
                cadena += "P${n.toInt()} = [(λ/μ)^n/S^(n-S)S!]P0 = [${formateador(Math.pow((lambda/mu).toDouble(),n),long)}/${formateador(((Math.pow(s.toDouble(),n-s))*(factorial(s.toLong()))),long)}](${formateador(p0,long)}) = ${formateador((((Math.pow((lambda/mu).toDouble(),n))/(((Math.pow(s.toDouble(),n-s))*(factorial(s.toLong())))))*p0),long)}\n"
                acumulador += (((Math.pow((lambda/mu).toDouble(),n))/(((Math.pow(s.toDouble(),n-s))*(factorial(s.toLong())))))*p0)
                detener = (((Math.pow((lambda/mu).toDouble(),n))/(((Math.pow(s.toDouble(),n-s))*(factorial(s.toLong())))))*p0)
            }else{
                cadena += "P${n.toInt()} = [(λ/μ)^n/n!]P0 = [${formateador(Math.pow((lambda/mu).toDouble(),n),long)}/${factorial(n.toLong())}](${formateador(p0,long)}) = ${formateador((((Math.pow((lambda/mu).toDouble(),n))/factorial(n.toLong()))*p0),long)}\n"
                acumulador += (((Math.pow((lambda/mu).toDouble(),n))/factorial(n.toLong()))*p0)
                detener = (((Math.pow((lambda/mu).toDouble(),n))/factorial(n.toLong()))*p0)
            }

            cadena += "acumulado, p${n.toInt()} = ${formateador(acumulador, long)}\n\n"
            n++
        }
        textView26.text = cadena

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun formateador(string:Double, longitud:Int):String{
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

        val simpleAlert = AlertDialog.Builder(this@RespuestasMms).create()
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
