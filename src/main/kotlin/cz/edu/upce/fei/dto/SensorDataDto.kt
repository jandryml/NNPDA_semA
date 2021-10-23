package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Sensor
import cz.edu.upce.fei.model.SensorData

class SensorDataDto(
    var id: Long = Long.MIN_VALUE,
    var type: String = "",
    var value: String = "",
    var sensorId: Long = Long.MIN_VALUE
) {
    fun toModel(getSensor: (id: Long) -> Sensor?) = SensorData(id, type, value, getSensor(sensorId))
}
