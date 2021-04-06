package com.pavellukyanov.themartian.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

@Dao
interface RoverInfoDao {
    @Query("SELECT * FROM rover_info WHERE roverName = :roverName")
    suspend fun getRoverInfo(roverName: String): RoverInfoEntity

    @Query("SELECT * FROM rover_info")
    fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoverInfo(roverInfoEntity: RoverInfoEntity): Long

    @Update
    suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    @Query("DELETE FROM rover_info WHERE roverName = :roverName")
    suspend fun deleteRoverInfo(roverName: String)
}