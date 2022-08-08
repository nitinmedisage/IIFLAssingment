package com.elink.iiflassingment.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Nitin Sabale
 * @since 03-08-2022. 15:27
 * this class use for room database table and entity class
 */
@Entity(tableName = "watchListTbl")
open class ChildWatchListEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "ID")
        val id: Int?,

        @ColumnInfo(name = "GroupID")
        val groupId: String? = null,

        @ColumnInfo(name = "orientation")
        val orientation: String? = null,

        @ColumnInfo(name = "SortBy")
        val SortBy: String? = null,

        @ColumnInfo(name = "VolumeSort")
        val volumeSort: Int? = null,

        @ColumnInfo(name = "LastTradePriceSort")
        val lastTradePriceSort: Int? = null,

        @ColumnInfo(name = "PCloseSort")
        val PCloseSort: Int? = null,

        @ColumnInfo(name = "DayHigh")
        @SerializedName("DayHigh")
        val dayHigh: Float? = null,

        @ColumnInfo(name = "DayLow")
        @SerializedName("DayLow")
        val dayLow: Float? = null,

        @ColumnInfo(name = "EPS")
        @SerializedName("EPS")
        val EPS: Int? = null,

        @ColumnInfo(name = "Exch")
        @SerializedName("Exch")
        val exch: String? = null,

        @ColumnInfo(name = "ExchType")
        @SerializedName("ExchType")
        val exchType: String? = null,

        @ColumnInfo(name = "FullName")
        @SerializedName("FullName")
        val fullName: String? = null,

        @ColumnInfo(name = "High52Week")
        @SerializedName("High52Week")
        val high52Week: Int? = null,

        @ColumnInfo(name = "LastTradePrice")
        @SerializedName("LastTradePrice")
        val lastTradePrice: Double? = null,

        @ColumnInfo(name = "LastTradeTime")
        @SerializedName("LastTradeTime")
        val lastTradeTime: String? = null,

        @ColumnInfo(name = "Low52Week")
        @SerializedName("Low52Week")
        val low52Week: Int? = null,

        @ColumnInfo(name = "Month")
        @SerializedName("Month")
        val month: Int? = null,

        @ColumnInfo(name = "NseBseCode")
        @SerializedName("NseBseCode")
        val nseBseCode: Double? = null,

        @ColumnInfo(name = "PClose")
        @SerializedName("PClose")
        val pClose: Float? = null,

        @ColumnInfo(name = "Quarter")
        @SerializedName("Quarter")
        val quarter: Int? = null,

        @ColumnInfo(name = "ScripCode")
        @SerializedName("ScripCode")
        val scripCode: Double? = null,

        @ColumnInfo(name = "ShortName")
        @SerializedName("ShortName")
        val shortName: String? = null,

        @ColumnInfo(name = "Volume")
        @SerializedName("Volume")
        val volume: Double? = null,

        @ColumnInfo(name = "Year")
        @SerializedName("Year")
        val year: Int? = null
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readString(),
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Int::class.java.classLoader) as? Int) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(groupId)
                parcel.writeString(orientation)
                parcel.writeString(SortBy)
                parcel.writeValue(volumeSort)
                parcel.writeValue(lastTradePriceSort)
                parcel.writeValue(PCloseSort)
                parcel.writeValue(dayHigh)
                parcel.writeValue(dayLow)
                parcel.writeValue(EPS)
                parcel.writeString(exch)
                parcel.writeString(exchType)
                parcel.writeString(fullName)
                parcel.writeValue(high52Week)
                parcel.writeValue(lastTradePrice)
                parcel.writeString(lastTradeTime)
                parcel.writeValue(low52Week)
                parcel.writeValue(month)
                parcel.writeValue(nseBseCode)
                parcel.writeValue(pClose)
                parcel.writeValue(quarter)
                parcel.writeValue(scripCode)
                parcel.writeString(shortName)
                parcel.writeValue(volume)
                parcel.writeValue(year)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<ChildWatchListEntity> {
                override fun createFromParcel(parcel: Parcel): ChildWatchListEntity {
                        return ChildWatchListEntity(parcel)
                }

                override fun newArray(size: Int): Array<ChildWatchListEntity?> {
                        return arrayOfNulls(size)
                }
        }
}

