package com.pavellukyanov.themartian.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity

@Dao
interface RoverInfoDao {
    @Query("SELECT * FROM rover_info")
    fun getAllRoversInfo(): LiveData<List<RoverInfoEntity>>

    @Query("SELECT * FROM rover_info WHERE id = :name")
    fun getRoverInfo(name: Long): LiveData<RoverInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoverInfo(roverInfoEntity: RoverInfoEntity): Long

    @Update
    suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    @Query("DELETE FROM rover_info WHERE rover_name = :name")
    suspend fun deleteRoverInfo(name: String)
}