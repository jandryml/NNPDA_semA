package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Sensor
import cz.edu.upce.fei.model.SensorData
import cz.edu.upce.fei.model.SensorDataType
import java.text.SimpleDateFormat

class SensorDataDto(
    var id: Long = Long.MIN_VALUE,
    var dataType: String,
    var value: String,
    var sensorId: Long = Long.MIN_VALUE,
    var createdOn: String,
    var updatedOn: String = ""
) {
    fun toModel(getSensor: (id: Long) -> Sensor?) =
        SensorData(id, SensorDataType.valueOf(dataType), value, getSensor(sensorId), dateFormat.parse(createdOn))

    companion object {
        val dateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS")
    }
}
