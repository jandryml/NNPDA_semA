package cz.edu.upce.fei.service

import cz.edu.upce.fei.dto.SensorDto
import cz.edu.upce.fei.model.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.yaml.snakeyaml.Yaml

@Service
class DeviceConfigService(
    @Autowired val deviceService: DeviceService,
    @Autowired val sensorService: SensorService
) {

    @Value("\${nnpda.server.url}")
    private val serverUrl: String? = null

    fun getConfigFile(deviceId: Long, regenerate: Boolean = false): ResponseEntity<Any> {
        return deviceService.findById(deviceId)?.let {
            if (it.config.isEmpty() || regenerate) {
                generateConfigFile(deviceId)
            } else {
                ResponseEntity.ok(deviceService.save(it.toDto()))
            }
        } ?: run {
            ResponseEntity.status(404).body("Device with ID $deviceId not found")
        }
    }

    fun generateConfigFile(deviceId: Long): ResponseEntity<Any> {
        return deviceService.findById(deviceId)?.let {
            val sensorList = sensorService.listFiltered(deviceId)
            val data = generateYamlStructure(it, sensorList)

            it.config = Yaml().dumpAsMap(data)
            return ResponseEntity.ok(deviceService.save(it.toDto()))
        } ?: run {
            ResponseEntity.status(404).body("Device with ID $deviceId not found")
        }
    }

    private fun generateYamlStructure(it: Device, sensorList: Iterable<SensorDto>) =
        mapOf(
            "serverUrl" to serverUrl,
            "device" to mapOf("id" to it.id, "name" to it.name),
            "sensors" to sensorList.map { sensor ->
                mapOf(
                    "id" to sensor.id,
                    "name" to sensor.name,
                    "dataType" to sensor.dataType
                )
            }
        )
}