package com.pavellukyanov.themartian.data.database.dao

import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RoverInfoDao {
    @Query("SELECT * FROM rover_info WHERE roverName = :roverName")
    fun getRoverInfo(roverName: String): Observable<RoverInfoEntity>

    @Query("SELECT * FROM rover_info")
    fun getAllRoverInfo(): Observable<List<RoverInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoverInfo(roverInfoEntity: RoverInfoEntity)

    @Update
    fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    @Query("DELETE FROM rover_info WHERE roverName = :roverName")
    fun deleteRoverInfo(roverName: String)
}