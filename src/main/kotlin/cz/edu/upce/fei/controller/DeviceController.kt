package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.DeviceDto
import cz.edu.upce.fei.service.DeviceConfigService
import cz.edu.upce.fei.service.DeviceService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/device")
@Api( tags = ["Device"])
class DeviceController(
    @Autowired val deviceService: DeviceService,
    @Autowired val deviceConfigService: DeviceConfigService
) {

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getFiltered(@RequestParam userId: Long): Iterable<DeviceDto> {
        return deviceService.listFiltered(userId)
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun saveDevice(@RequestBody deviceDto: DeviceDto): DeviceDto {
        return deviceService.save(deviceDto)
    }

    @GetMapping("/config/{deviceId}")
    @PreAuthorize("hasRole('USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    fun getDeviceConfig(@PathVariable deviceId: Long, @RequestParam regenerate: Boolean = false): ResponseEntity<Any> {
        return deviceConfigService.getConfigFile(deviceId, regenerate)
    }
}
