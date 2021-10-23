package cz.edu.upce.fei.service

import cz.edu.upce.fei.dto.DeviceDto
import cz.edu.upce.fei.model.Device
import cz.edu.upce.fei.repository.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeviceService(
    @Autowired val deviceRepository: DeviceRepository,
    @Autowired val userService: UserService
) {

    fun listFiltered(userId: Long): Iterable<DeviceDto> {
        return deviceRepository.findByUserId(userId).map { it.toDto() }
    }

    fun save(deviceDto: DeviceDto): DeviceDto {
        return deviceRepository.save(deviceDto.toModel { userService.findById(deviceDto.userId) }).toDto()
    }

    fun findById(deviceId: Long): Device? {
        return deviceRepository.findById(deviceId).orElse(null)
    }
}
