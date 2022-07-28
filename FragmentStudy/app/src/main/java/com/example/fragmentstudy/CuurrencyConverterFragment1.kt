package com.example.fragmentstudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlin.math.log

class CurrencyConverterFragment1: Fragment() {  //프래그먼트에 주생성자는 만들면 x
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



    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(    // 레이아웃을 뷰로 바꿔주는 역할 -> inflate
            R.layout.currency_converter_fragment1,  //복붙하고 레이아웃 이름만 바꾸셈
            container,
            false)  //무조건 false!


        val calculateBtn = view.findViewById<Button>(R.id.caculate)
        val amount = view.findViewById<EditText>(R.id.amount)
        val result = view.findViewById<TextView>(R.id.result)
        val fromCurrencySpinner = view.findViewById<Spinner>(R.id.from_currency)
        val toCurrencySpinner = view.findViewById<Spinner>(R.id.to_currency)

        // drop down spinner에 표시할 내용
        val currencySelectionArrayAdapter = ArrayAdapter<String>(
            view.context,
            android.R.layout.simple_spinner_item, // android.R은 이미 android에서 만들어놓은 리소스를 쓰겠다는 뜻
            listOf("USD", "EUR", "JPY", "KRW")
        )
        currencySelectionArrayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )


        // spinner에 연결
        fromCurrencySpinner.adapter = currencySelectionArrayAdapter
        toCurrencySpinner.adapter = currencySelectionArrayAdapter

        val itemSelectedListener = object : AdapterView.OnItemSelectedListener { // 'object :' 익명클래스 (2 이상)
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                result.text = calculateCurrency(
                    amount.text.toString().toDouble(),  // 돈
                    fromCurrencySpinner.selectedItem.toString(),    // first
                    toCurrencySpinner.selectedItem.toString()       // 바꿀값
                ).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
        fromCurrencySpinner.onItemSelectedListener = itemSelectedListener
        toCurrencySpinner.onItemSelectedListener = itemSelectedListener

        calculateBtn.setOnClickListener {
            result.text = calculateCurrency(
                amount.text.toString().toDouble(),
                fromCurrencySpinner.selectedItem.toString(),
                toCurrencySpinner.selectedItem.toString()
            ).toString()
        }

        // EditText의 내용이 바뀔때마다 계산을 다시 해줌
//        amount.addTextChangedListener{
//            result.text = calculateCurrency(
//                amount.text.toString().toDouble(),
//                fromCurrencySpinner.selectedItem.toString(),
//                toCurrencySpinner.selectedItem.toString()
//            ).toString()
//        }

        return view
    }
}