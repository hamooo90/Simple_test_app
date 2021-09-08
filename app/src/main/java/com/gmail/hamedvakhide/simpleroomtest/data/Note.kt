package com.gmail.hamedvakhide.simpleroomtest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class Note(
    var title:String,
    var content:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
): Parcelable
