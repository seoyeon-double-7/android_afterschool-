package com.example.fragmentstudy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.lang.Exception

class CurrencyConverterFragment3: Fragment() {  //프래그먼트에 주생성자는 만들면 x
    interface CurrencyCalculationListener{
        fun onCalculate(
            result: Double,
            amount: Double,
            from: String,
            to: String)
    }
    lateinit var listener: CurrencyCalculationListener  // 리스너는 엑티비티

    override fun onAttach(context: Context) { //
        super.onAttach(context)

        if(activity is CurrencyCalculationListener){    //타입확인
           listener = activity as CurrencyCalculationListener   // as는 형변환을 뜻함 (❁´◡`❁)
        }else{
            throw Exception("CurrencyCalculationListener 미구현")
        }


    }


    val currencyExchangeMap = mapOf(
        "USD" to 1.0,
        "EUR" to 0.9,
        "JPY" to 110.0,
        "KRW" to 1150.0
    )

    fun calculateCurrency(amount: Double, from: String, to: String): Double{    //from을 to로 변환
        var USDAmount = if(from != "USD") {
                (amount / currencyExchangeMap[from]!!)
        }else{
            amount
        }
        return currencyExchangeMap[to]!! * USDAmount
    }

    lateinit var fromCurrency: String
    lateinit var toCurrency: String

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(    // 레이아웃을 뷰로 바꿔주는 역할 -> inflate
            R.layout.currency_converter_fragment3,  //복붙하고 레이아웃 이름만 바꾸셈
            container,
            false)  //무조건 false!


        val calculateBtn = view.findViewById<Button>(R.id.caculate)
        val amount = view.findViewById<EditText>(R.id.amount)
        val exchangeType = view.findViewById<TextView>(R.id.exchange_type)



        fromCurrency = arguments?.getString("from", "USD")!!
        toCurrency = arguments?.getString("to", "USD")!!

        exchangeType.text = "${fromCurrency} => ${toCurrency} 변환"

        calculateBtn.setOnClickListener {

            val result = calculateCurrency(
                amount.text.toString().toDouble(),
                fromCurrency,
                toCurrency
            )

//            if(result.text === null){
//                Toast.makeText(activity, "Hello World!", Toast.LENGTH_SHORT).show()
//            }
            // TODO : result값을 액티비티로 전달
            listener.onCalculate(
                result,
                amount.text.toString().toDouble(),
                fromCurrency, toCurrency)
        }

        return view
    }

    companion object{   //정적 속성, 메소드를 만듬
        fun newInstance(from: String, to: String): CurrencyConverterFragment3{
            val fragment = CurrencyConverterFragment3()

            //번들 객체를 만들고 필요한 데이터 저장
            val args = Bundle()
            args.putString("from", from)
            args.putString("to", to)
            fragment.arguments = args

            return fragment
        }
    }
}