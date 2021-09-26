package cz.edu.upce.fei.repository

import cz.edu.upce.fei.model.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DeviceRepository : JpaRepository<Device, Long> {
    @Query(value = "SELECT * FROM device d WHERE d.user_id = :userId", nativeQuery = true)
    fun findByUserId(userId: Long): Iterable<Device>
}