package com.example.lab2_mobile_native

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize

@Parcelize
data class Activity(

    var title: String,
    var date: String,
    var time: String,
    var duration: String,
    var description: String,
    var priority: String,
    var id: Int
) : Parcelable {

    companion object {
        var currentId = 0
    }

    constructor(
        title: String,
        date: String,
        time: String,
        duration: String,
        description:String,
        priority: String
    ) : this(title, date, time, duration,description,priority, currentId++) {
        Log.i("Model activity Class: ", "CurrentId is $currentId")
    }
}
