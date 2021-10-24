package cz.edu.upce.fei.model

import cz.edu.upce.fei.dto.SensorDataDto
import java.util.*
import javax.persistence.*

@Entity(name = "sensor_data")
class SensorData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    var dataType: SensorDataType = SensorDataType.OTHER,
    var value: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id")
    var sensor: Sensor? = null,
    var createdOn: Date = Date(),
    var updatedOn: Date? = null,
) {
    fun toDto() =
        SensorDataDto(
            id, dataType.name, value, sensor?.id ?: Long.MIN_VALUE,
            SensorDataDto.dateFormat.format(createdOn),
            SensorDataDto.dateFormat.format(updatedOn)
        )

}
