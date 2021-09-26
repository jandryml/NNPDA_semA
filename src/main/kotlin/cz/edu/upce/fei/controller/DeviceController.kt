package cz.edu.upce.fei.controller

import cz.edu.upce.fei.dto.DeviceDto
import cz.edu.upce.fei.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/device")
class DeviceController(
    @Autowired val deviceService: DeviceService
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
}