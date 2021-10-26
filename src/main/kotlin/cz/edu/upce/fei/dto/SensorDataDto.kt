package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Sensor
import cz.edu.upce.fei.model.SensorData
import cz.edu.upce.fei.model.SensorDataType
import java.text.SimpleDateFormat
import java.util.*

data class SensorDataDto(
    var id: Long = Long.MIN_VALUE,
    var dataType: String,
    var value: Long,
    var sensorId: Long = Long.MIN_VALUE,
    var createdOn: String,
    var updatedOn: String = ""
) {
    fun toModel(getDataType: (name: String) -> SensorDataType, getSensor: (id: Long) -> Sensor?) =
        SensorData(id, getDataType(dataType), value, getSensor(sensorId), parseDate(createdOn))

    companion object {
        //thread safe
        fun parseDate(value: String): Date {
            val formatter = SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS")
            return formatter.parse(value);
        }
        val dateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS")
    }
}
