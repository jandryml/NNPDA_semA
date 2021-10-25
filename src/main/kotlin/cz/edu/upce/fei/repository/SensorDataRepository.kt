package cz.edu.upce.fei.repository

import cz.edu.upce.fei.model.SensorData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SensorDataRepository : JpaRepository<SensorData, Long> {
    @Query(value = "SELECT * FROM sensor_data sd WHERE sd.sensor_id = :sensorId", nativeQuery = true)
    fun findBySensorId(sensorId: Long): List<SensorData>
}
