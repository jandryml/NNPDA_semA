package cz.edu.upce.fei.model

import cz.edu.upce.fei.dto.SensorDataDto
import javax.persistence.*

@Entity(name = "sensor_data")
class SensorData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    var type: String = "",
    var value: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id")
    val sensor: Sensor? = null
) {
    fun toDto(): SensorDataDto {
        return SensorDataDto(id, type, value, sensor?.id ?: Long.MIN_VALUE)
    }
}
