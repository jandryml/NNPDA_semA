package cz.edu.upce.fei.dto

import cz.edu.upce.fei.model.Device
import cz.edu.upce.fei.model.Sensor

class SensorDto(
    var id: Long = Long.MIN_VALUE,
    var name: String = "",
    var deviceId: Long = Long.MIN_VALUE,
    var deviceName: String = ""
) {
    fun toModel(getDevice: (id: Long) -> Device?) = Sensor(id, name, getDevice(deviceId))
}