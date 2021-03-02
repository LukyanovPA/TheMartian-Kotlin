package com.pavellukyanov.themartian.data.models.dbmodel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pavellukyanov.themartian.data.models.networkmodel.PhotosInfo

@Entity(tableName = "rover_info")
class RoverInfoEntity(
    @ColumnInfo(name = "rover_name") var name: String,
    @ColumnInfo(name = "rover_picture_in_main_page") var roverPictureInMainPage: Int,
    @ColumnInfo(name = "rover_picture_in_details_page") var roverPictureInDetailsPage: Int,
    @ColumnInfo(name = "landing_date") var landingDate: String,
    @ColumnInfo(name = "launch_date") var launchData: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "max_sol") var maxSol: Long,
    @ColumnInfo(name = "max_date") var maxDate: String,
    @ColumnInfo(name = "total_photos") var totalPhotos: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}