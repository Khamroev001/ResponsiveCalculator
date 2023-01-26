package khamroev001.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button
    private lateinit var zero: Button

    private lateinit var point: Button
    private lateinit var clear: Button
    private lateinit var delete: Button

    private lateinit var div: Button
    private lateinit var multiply: Button
    private lateinit var plus: Button
    private lateinit var minus: Button

    private lateinit var oper: TextView
    private lateinit var result: TextView

    private lateinit var switchBtn: Switch

    private var isPoint = true
    private var isSymbol = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        one.setOnClickListener(this)
        two.setOnClickListener(this)
        three.setOnClickListener(this)
        four.setOnClickListener(this)
        five.setOnClickListener(this)
        six.setOnClickListener(this)
        seven.setOnClickListener(this)
        eight.setOnClickListener(this)
        nine.setOnClickListener(this)
        zero.setOnClickListener(this)

        point.setOnClickListener {
            if (isPoint && oper.text[oper.text.length - 1].isDigit()) {
                oper.text = oper.text.toString() + "."
                isPoint = false
                isSymbol = false
            }
        }

        clear.setOnClickListener { clear() }

        delete.setOnClickListener {
            if (oper.text.length == 1) {
                clear()
            } else {
                if (oper.text[oper.text.length - 1] == '.') {
                    isPoint = true
                }
                if (!oper.text[oper.text.length - 1].isDigit()) {
                    isSymbol = true
                }
                oper.text = oper.text.substring(0, oper.text.length - 1)
            }

            var str: String = oper.text.toString()

            if (!oper.text[oper.text.length - 1].isDigit()) {
                oper.text = oper.text.substring(0, oper.text.length - 1)
                result.text = calculate()
                oper.text = str
            } else {
                result.text = calculate()
            }
        }

        div.setOnClickListener { addSymbol(div.text.toString()) }
        multiply.setOnClickListener { addSymbol(multiply.text.toString()) }
        plus.setOnClickListener { addSymbol(plus.text.toString()) }
        minus.setOnClickListener { addSymbol(minus.text.toString()) }

        switchBtn.setOnClickListener {
            if (switchBtn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun initUI() {
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        zero = findViewById(R.id.zero)

        oper = findViewById(R.id.operand)
        result = findViewById(R.id.result)

        clear = findViewById(R.id.restart)
        point = findViewById(R.id.dot)
        delete = findViewById(R.id.delete)

        div = findViewById(R.id.division)
        multiply = findViewById(R.id.multiply)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)

        switchBtn = findViewById(R.id.themeSwitcher)
    }

    override fun onClick(p0: View?) {
        val btn = findViewById<Button>(p0!!.id)
        if (oper.text != "0") {
            oper.text = oper.text.toString() + btn.text
        } else {
            oper.text = btn.text
        }

        result.text = calculate()
        isSymbol = true
    }

    private fun calculate(): String {
        var res: Any = "0"
        var myList: MutableList<Any> = createArray(oper.text.toString())
        var i = 0
        var j = 0

        if (myList.size >= 3 && myList.size % 2 != 0) {
            while (i < myList.size) {
                if ((myList[i].toString() == "×") || (myList[i].toString() == "÷")) {
                    if (myList[i].toString() == "×") {
                        res = (myList[i - 1].toString().toDouble()) * (myList[i + 1].toString()
                            .toDouble())
                        replace(i, myList)
                        myList.add(i - 1, res)
                        i -= 2
                    } else {
                        res = (myList[i - 1].toString().toDouble()) / (myList[i + 1].toString()
                            .toDouble())
                        replace(i, myList)
                        myList.add(i - 1, res)
                        i -= 2
                    }
                }
                i++
            }
            while (j < myList.size) {
                if ((myList[j].toString() == "+") || (myList[j].toString() == "-")) {
                    if (myList[j].toString() == "+") {
                        res = (myList[j - 1].toString().toDouble()) + (myList[j + 1].toString()
                            .toDouble())
                        replace(j, myList)
                        myList.add(j - 1, res)
                        j -= 2
                    } else {
                        res = (myList[j - 1].toString().toDouble()) - (myList[j + 1].toString()
                            .toDouble())
                        replace(j, myList)
                        myList.add(j - 1, res)
                        j -= 2
                    }
                }
                j++
            }
        }
        val number:Double = 0.0449999
        val number3digits:Double = String.format("%.3f", number).toDouble()
        val number2digits:Double = String.format("%.2f", number3digits).toDouble()
        val solution:Double = String.format("%.1f", number2digits).toDouble()
        if (myList.size != 1) {

            res = (Math.round(res.toString().toDouble() * 10.0) / 10.0).toString()
        }
        if (myList.size == 1) {
            res = myList[0].toString()
        }

        return res.toString()
    }

    private fun clear() {
        oper.text = "0"
        result.text = "0"
        isPoint = true
        isSymbol = false
    }

    private fun replace(i: Int, myList: MutableList<Any>) {
        myList.removeAt(i)
        myList.removeAt(i)
        myList.removeAt(i - 1)
    }

    private fun addSymbol(symbol: String) {
        if (isSymbol && oper.text[oper.text.length - 1] != '.') {
            oper.text = oper.text.toString() + symbol
            isSymbol = false
        } else {
            if (oper.text != "0" && oper.text[oper.text.length - 1] != '.') {
                oper.text = oper.text.substring(0, oper.text.length - 1) + symbol
            }
        }
        isPoint = true
    }

    private fun createArray(operText: String): MutableList<Any> {
        var list = mutableListOf<Any>()

        var temp = ""
        for (i in operText) {
            if (i.isDigit() || i == '.') {
                temp += i
            } else {
                list.add(temp)
                list.add(i)
                temp = ""
            }
        }
        if (temp.isNotEmpty()) {
            list.add(temp)
        }
        return list
    }

}