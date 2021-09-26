package cz.edu.upce.fei.repository

import cz.edu.upce.fei.model.Sensor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SensorRepository : JpaRepository<Sensor, Long> {
    @Query(value = "SELECT * FROM sensor d WHERE d.device_id = :deviceId", nativeQuery = true)
    fun findByDeviceId(deviceId: Long): Iterable<Sensor>
}