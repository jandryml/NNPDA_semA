package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.SensorDataDto
import cz.edu.upce.fei.service.SensorDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sensor-data")
class SensorDataController(
    @Autowired val sensorDataService: SensorDataService
) {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getFiltered(@RequestParam sensorId: Long): Iterable<SensorDataDto> {
        return sensorDataService.listFiltered(sensorId)
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun saveSensorData(@RequestBody sensorDataDto: SensorDataDto): SensorDataDto {
        return sensorDataService.save(sensorDataDto)
    }
}
