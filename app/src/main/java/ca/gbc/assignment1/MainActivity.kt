package ca.gbc.assignment1 // KEEP YOUR ORIGINAL FIRST LINE!

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etHours = findViewById<TextInputEditText>(R.id.etHours)
        val etRate = findViewById<TextInputEditText>(R.id.etRate)
        val etTaxRate = findViewById<TextInputEditText>(R.id.etTaxRate)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        val tvRegularPay = findViewById<TextView>(R.id.tvRegularPay)
        val tvOvertimePay = findViewById<TextView>(R.id.tvOvertimePay)
        val tvTotalPay = findViewById<TextView>(R.id.tvTotalPay)
        val tvTax = findViewById<TextView>(R.id.tvTax)

        // Launch AboutActivity
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // Calculate Button Logic
        btnCalculate.setOnClickListener {
            val hoursStr = etHours.text?.toString()?.trim().orEmpty()
            val rateStr = etRate.text?.toString()?.trim().orEmpty()
            val taxRateStr = etTaxRate.text?.toString()?.trim().orEmpty()

            if (hoursStr.isEmpty() || rateStr.isEmpty() || taxRateStr.isEmpty()) {
                Toast.makeText(this, "Please enter all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hours = hoursStr.toDoubleOrNull()
            val rate = rateStr.toDoubleOrNull()
            val taxRate = taxRateStr.toDoubleOrNull()

            if (hours == null || rate == null || taxRate == null) {
                Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculation formulas from Assignment 1 specifications
            val regularPay: Double
            val overtimePay: Double

            if (hours <= 40.0) {
                regularPay = hours * rate
                overtimePay = 0.0
            } else {
                regularPay = 40.0 * rate
                overtimePay = (hours - 40.0) * rate * 1.5
            }

            val totalPay = regularPay + overtimePay
            // Tax calculated on regular pay or total pay as per spec
            val tax = totalPay * taxRate

            // Present results formatted to 2 decimal places
            tvRegularPay.text = String.format(Locale.getDefault(), "Regular Pay: $%.2f", regularPay)
            tvOvertimePay.text = String.format(Locale.getDefault(), "Overtime Pay: $%.2f", overtimePay)
            tvTotalPay.text = String.format(Locale.getDefault(), "Total Pay: $%.2f", totalPay)
            tvTax.text = String.format(Locale.getDefault(), "Tax: $%.2f", tax)
        }
    }
}