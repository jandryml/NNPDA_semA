package cz.edu.upce.fei.service

import cz.edu.upce.fei.model.SensorDataType
import cz.edu.upce.fei.repository.SensorDataTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SensorDataTypeService(
    @Autowired val sensorDataTypeRepository: SensorDataTypeRepository
) {
    fun getAllTypes(): Iterable<String> {
        return sensorDataTypeRepository.findAll().map { it.name }
    }

    fun findOrSave(name: String): SensorDataType {
        val type = sensorDataTypeRepository.findByName(name.uppercase())
        return if (type.isPresent) {
            type.get()
        } else {
            sensorDataTypeRepository.save(SensorDataType(name = name.uppercase()))
        }
    }
}
