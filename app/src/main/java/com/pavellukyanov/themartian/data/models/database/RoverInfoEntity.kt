package com.pavellukyanov.themartian.data.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.pavellukyanov.themartian.R
import java.util.*

@Entity(tableName = "rover_info")
data class RoverInfoEntity(
    @ColumnInfo(name = "rover_name") var name: String,
    @ColumnInfo(name = "landing_date") var landingDate: String,
    @ColumnInfo(name = "launch_date") var launchData: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "max_sol") var maxSol: Long,
    @ColumnInfo(name = "max_date") var maxDate: String,
    @ColumnInfo(name = "total_photos") var totalPhotos: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @Ignore
    lateinit var mainPagePicture: Object
    @Ignore
    lateinit var detailsPagePicture: Object
}