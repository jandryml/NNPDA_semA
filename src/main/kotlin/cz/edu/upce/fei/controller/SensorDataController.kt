package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.SensorDataDto
import cz.edu.upce.fei.service.SensorDataService
import cz.edu.upce.fei.service.SensorDataTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sensor-data")
class SensorDataController(
    @Autowired val sensorDataService: SensorDataService,
    @Autowired val sensorDataTypeService: SensorDataTypeService
) {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getFiltered(@RequestParam sensorId: Long): Iterable<SensorDataDto> {
        return sensorDataService.listFiltered(sensorId)
    }

    @GetMapping("/types")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getTypes() : Iterable<String> {
        return sensorDataTypeService.getAllTypes()
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun saveSensorData(@RequestBody sensorDataDto: SensorDataDto): SensorDataDto {
        //TODO remove
        println(sensorDataDto)

        return sensorDataService.save(sensorDataDto)
    }
}
