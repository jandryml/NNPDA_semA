package cz.edu.upce.fei.model

import cz.edu.upce.fei.dto.SensorDataDto
import java.util.*
import javax.persistence.*

@Entity(name = "sensor_data")
class SensorData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_type_id")
    var dataType: SensorDataType = SensorDataType(name = "DEFAULT"),
    var value: Long = 0,
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
