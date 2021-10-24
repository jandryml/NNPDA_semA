package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Device
import cz.edu.upce.fei.model.Sensor
import cz.edu.upce.fei.model.SensorDataType

class SensorDto(
    var id: Long = Long.MIN_VALUE,
    var name: String,
    var dataType: String,
    var deviceId: Long = Long.MIN_VALUE,
    var deviceName: String = ""
) {
    fun toModel(getDataType: (name: String) -> SensorDataType, getDevice: (id: Long) -> Device?) =
        Sensor(id, name, getDevice(deviceId), getDataType(dataType))
}
