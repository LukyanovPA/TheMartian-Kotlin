package com.pavellukyanov.themartian.data.database

import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoverInfoDao {
    @Query("SELECT * FROM rover_info WHERE roverName = :roverName")
    fun getRoverInfo(roverName: String): Single<RoverInfoEntity>

    @Query("SELECT * FROM rover_info")
    fun getAllRoverInfo(): Single<List<RoverInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoverInfo(roverInfoEntity: RoverInfoEntity): Completable

    @Update
    fun updateRoverInfo(roverInfoEntity: RoverInfoEntity): Completable

    @Query("DELETE FROM rover_info WHERE roverName = :roverName")
    fun deleteRoverInfo(roverName: String): Completable
}