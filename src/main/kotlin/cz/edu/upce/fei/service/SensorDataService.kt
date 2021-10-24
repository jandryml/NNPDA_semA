package cz.edu.upce.fei.service

import cz.edu.upce.fei.dto.SensorDataDto
import cz.edu.upce.fei.repository.SensorDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SensorDataService(
    @Autowired val sensorDataRepository: SensorDataRepository,
    @Autowired val sensorService: SensorService,
    @Autowired val sensorDataTypeService: SensorDataTypeService
) {

    fun listFiltered(sensorId: Long): Iterable<SensorDataDto> {
        return sensorDataRepository.findBySensorId(sensorId).map { it.toDto() }
    }

    fun save(sensorDataDto: SensorDataDto): SensorDataDto {
        return sensorDataRepository.save(sensorDataDto.toModel(
            { sensorDataTypeService.findOrSave(sensorDataDto.dataType) },
            { sensorService.findById(sensorDataDto.sensorId) })
            .apply { updatedOn = Date() })
            .toDto()
    }
}
