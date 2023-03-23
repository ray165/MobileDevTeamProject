package com.example.mobiledevteamproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MemeData(val name: String = "something", val date: String, val imgResourceId: Int): Parcelable {
}