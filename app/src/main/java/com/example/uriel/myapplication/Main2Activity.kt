package com.example.uriel.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.FileInputStream


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        title = "Resultados"
        supportActionBar?.subtitle = "M/M/1"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val valores = intent.getDoubleArrayExtra("valores")
        val lambda = valores[0]
        val mu = valores[1]
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
        textView.text = "Lambda = ${formateador(lambda,long)} y Mu = ${formateador(mu,long)}"

        /*val params = view.layoutParams
        params.height = textView.layoutParams.height
        view.layoutParams = params
        println("textView=${textView.layoutParams.height} \n" +
                "View=${view.layoutParams.height}")*/

        textView7.text = "ρ = λ/μ = ${formateador(lambda,long)}/${formateador(mu,long)} = ${formateador(lambda/mu,long)} = ${formateador((lambda/mu)*100,long)}%"
        //textView8.text = "Po = 1-ρ = 1-${lambda/mu} = ${1-(lambda/mu)} = ${(1-(lambda/mu))*100}%"
        textView2.text = "L = λ/(μ-λ) = ${formateador(lambda,long)}/(${formateador(mu,long)}-${formateador(lambda,long)}) = ${formateador(lambda,long)}/${formateador(mu-lambda,long)} = ${formateador(lambda/(mu-lambda),long)} cliente(s)"
        textView3.text = "Lq = (λ^2)/(μ*(μ-λ)) = ${formateador((Math.pow(lambda.toDouble(),2.0)),long)}/${formateador((mu*(mu-lambda)),long)} = ${formateador((Math.pow(lambda,2.0)/(mu*(mu-lambda))),long)} cliente(s)"
        textView6.text = "Tq = λ/(μ*(μ-λ)) = ${formateador(lambda,long)}/${formateador(mu*(mu-lambda),long)} = ${formateador(lambda/(mu*(mu-lambda)), long)}"
        textView4.text = "W = 1/(μ-λ) = 1/${formateador(mu-lambda, long)} = ${formateador(1/(mu-lambda), long)} h = ${formateador((1/(mu-lambda))*60, long)} minutos"
        textView5.text = "Wq = Lq/λ = ${formateador((Math.pow(lambda.toDouble(),2.0)/(mu*(mu-lambda))),long)}/${formateador(lambda,long)} = ${formateador(((Math.pow(lambda,2.0)/(mu*(mu-lambda)))/lambda),long)} h = ${formateador((((Math.pow(lambda,2.0)/(mu*(mu-lambda)))/lambda)*60), long)} minutos"
        var acumulador = 0.0
        var cadena = ""
        var n = 0.0
        while(acumulador<=1.0 && n<=100.0 && (Math.pow((lambda/mu*1.0),n)*(1-(lambda/mu)))>0.00001){
            cadena += "P${n.toInt()} = ρ^${n.toInt()}(1-${formateador(lambda/mu, long)}) = ${formateador((Math.pow((lambda/mu*1.0),n)*(1-(lambda/mu))), long)} = ${formateador(((Math.pow((lambda/mu*1.0),n)*(1-(lambda/mu)))*100), long)}%\n"
            acumulador += Math.pow((lambda/mu*1.0),n)*(1-(lambda/mu))
            cadena += "acumulado, p${n.toInt()} = ${formateador(acumulador, long)}\n\n"
            n++
        }
        textView12.text = cadena
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun formateador(string:Double, longitud:Int):String{
        when {
            string.isInfinite() -> return "∞"
            string%1 == 0.0 -> return "${string.toInt()}"
            string.toString().length>longitud -> return String.format("%.${longitud}f",string)
            else -> return string.toString()
        }

    }


}
