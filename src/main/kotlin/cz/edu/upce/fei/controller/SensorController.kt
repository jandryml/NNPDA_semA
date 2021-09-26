package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.SensorDto
import cz.edu.upce.fei.service.SensorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sensor")
class SensorController(
    @Autowired val sensorService: SensorService
) {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getFiltered(@RequestParam deviceId: Long): Iterable<SensorDto> {
        return sensorService.listFiltered(deviceId)
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun saveSensor(@RequestBody sensorDto: SensorDto): SensorDto {
        return sensorService.save(sensorDto)
    }
}