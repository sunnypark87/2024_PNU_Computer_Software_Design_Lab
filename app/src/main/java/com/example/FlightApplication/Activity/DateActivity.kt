package com.example.FlightApplication.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.FlightApplication.R
import com.example.FlightApplication.databinding.SelectDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateActivity : AppCompatActivity() {
    private lateinit var binding: SelectDateBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectDateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val getIntent: Intent = getIntent()
        val city: String = getIntent.getStringExtra("city").toString()
        var minTime: String = "now"

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CANCELED) {
                setResult(RESULT_CANCELED)
                finish()
            }
        }

        binding.selStartDate.setOnClickListener {
            showDatePickerDialog(minTime) { selectedDate ->
                Log.d("Date", selectedDate)
                binding.selStartDate.text = selectedDate
                minTime = binding.selStartDate.text.toString()
            }
        }

        binding.selEndDate.setOnClickListener {
            showDatePickerDialog(minTime) { selectedDate ->
                binding.selEndDate.text = selectedDate
                minTime = "now"
            }
        }

        binding.selectDateBtn.setOnClickListener {
            val dateFormatter = SimpleDateFormat("d/m/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            val compareFormat = SimpleDateFormat("yyyymmdd",Locale.getDefault())
            val startDate = binding.selStartDate.text.toString()
            val endDate = binding.selEndDate.text.toString()
            Log.d("API", startDate)
            Log.d("API", endDate)


            if(startDate == "클릭하여 날짜 선택" || endDate == "클릭하여 날짜 선택"){
                Toast.makeText(this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                val date1 = dateFormatter.parse(startDate)
                val date2 = dateFormatter.parse(endDate)
                Log.d("API", outputFormat.format(date1))
                Log.d("API", outputFormat.format(date2))
                if (compareFormat.format(date1).toInt() <= compareFormat.format(date2).toInt()) {
                    val intent = Intent(this, DepartureSelectActivity::class.java)
                    intent.putExtra("city", city)
                    intent.putExtra("startDate", outputFormat.format(date1))
                    intent.putExtra("endDate", outputFormat.format(date2))
                    activityResultLauncher.launch(intent)
                } else {
                    Toast.makeText(this, "도착 시간이 출발 시간보다 빠릅니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.backToCountryBtn.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun showDatePickerDialog(minTime: String, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()

        val datePickerView = layoutInflater.inflate(R.layout.date_picker_dialog, null)
        val datePicker: DatePicker = datePickerView.findViewById(R.id.datePicker)

        // 선택할 수 있는 날짜 조정
        if (minTime == "now") {
            datePicker.minDate = calendar.timeInMillis
        }
        else {
            val time: List<String> = minTime.split("/")
            val year = time[2].toInt()
            val month = time[1].toInt() - 1
            val date = time[0].toInt()
            val minDateCalendar = Calendar.getInstance().apply {
                set(year, month, date, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            datePicker.minDate = minDateCalendar.timeInMillis
        }

        val dialog = AlertDialog.Builder(this)
            .setView(datePickerView)
            .setPositiveButton("확인") { _, _ ->
                val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
                onDateSelected(selectedDate)
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }
}