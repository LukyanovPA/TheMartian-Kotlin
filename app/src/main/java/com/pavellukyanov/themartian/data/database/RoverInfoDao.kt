package com.pavellukyanov.themartian.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pavellukyanov.themartian.data.models.database.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.network.RoverInfo

@Dao
interface RoverInfoDao {
    @Query("SELECT * FROM rover_info")
    fun getAllRoversInfo(): LiveData<List<RoverInfoEntity>>

    @Query("SELECT rover_name FROM rover_info WHERE rover_name = :name")
    suspend fun getRoverInfo(name: String): String

    @Query("SELECT max_date FROM rover_info WHERE rover_name = :roverName")
    suspend fun getRoverMaxDate(roverName: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoverInfo(roverInfo: RoverInfoEntity): Long

    @Update
    suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    @Query("DELETE FROM rover_info WHERE rover_name = :name")
    suspend fun deleteRoverInfo(name: String)

    @Query("DELETE FROM rover_info")
    suspend fun deleteAll()
}