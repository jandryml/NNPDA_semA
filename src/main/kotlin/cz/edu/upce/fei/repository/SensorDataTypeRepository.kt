package cz.edu.upce.fei.repository

import cz.edu.upce.fei.model.SensorDataType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SensorDataTypeRepository : JpaRepository<SensorDataType, Long> {
    fun findByName(name: String): Optional<SensorDataType>
}
