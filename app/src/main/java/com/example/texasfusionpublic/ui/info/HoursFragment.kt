package com.example.texasfusionpublic.ui.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import com.example.texasfusionpublic.databinding.FragmentHoursBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_hours.view.*

class HoursFragment : Fragment() {

    private lateinit var mViewModel: InfoViewModel
    private lateinit var binding: FragmentHoursBinding
    private lateinit var database: DatabaseReference

    data class Schedule(
        val dayOfWeek: String,
        val startTime: String,
        val startAbbr: String,
        val endTime: String,
        val endAbbr: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProviders.of(this).get(InfoViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_hours,container,false)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach{
                    if(it.key == "HoursOfOp") {
                        Log.i("HoursFragment", "New data: ${it.value}")
                        val recycler: RecyclerView = binding.recyclerView
                        recycler.apply {
                            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                            adapter = HoursAdapter(mViewModel.sortData(it.value.toString())) { schedule: Schedule -> editTime(schedule) }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("HoursFragment", "Failed to read value.", error.toException())
            }
        })

        binding.btnApply.setOnClickListener{
            binding.timeEditor.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            writeToDB()
        }

        binding.btnCancel.setOnClickListener{
            binding.timeEditor.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun editTime(schedule: Schedule){
        binding.recyclerView.apply{
            visibility = View.GONE
        }
        binding.timeEditor.apply{
            visibility = View.VISIBLE
            dayChosen.text = schedule.dayOfWeek
            timePicker.hour = get24HrFormat(schedule.startTime.substringBefore(":"), schedule.startAbbr)
            timePicker.minute = schedule.startTime.substringAfter(":").toInt()
            radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{_, checkedId ->
                when(checkedId){
                    radioStart.id -> {
                        timePicker.hour = get24HrFormat(schedule.startTime.substringBefore(":"), schedule.startAbbr)
                        timePicker.minute = schedule.startTime.substringAfter(":").toInt()
                    }
                    radioEnd.id -> {
                        timePicker.hour = get24HrFormat(schedule.endTime.substringBefore(":"), schedule.startAbbr)
                        timePicker.minute = schedule.endTime.substringAfter(":").toInt()
                    }
                }
            })
        }
    }

    private fun get24HrFormat(hour: String, abbr: String): Int{
        if(abbr.contains("a")){
            if(hour.toInt() == 12)
                return 0
            else
                return hour.toInt()
        } else
            return hour.toInt() + 12
    }

    private fun writeToDB(){

    }

}