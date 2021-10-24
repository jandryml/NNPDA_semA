package cz.edu.upce.fei.model

import cz.edu.upce.fei.dto.SensorDto
import javax.persistence.*

@Entity(name = "sensor")
class Sensor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = Long.MIN_VALUE,
    var name: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id")
    val device: Device? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_type_id")
    var dataType: SensorDataType = SensorDataType(name = "DEFAULT"),
) {
    fun toDto(): SensorDto {
        return SensorDto(id, name, dataType.name, device?.id ?: Long.MIN_VALUE, device?.name ?: "")
    }
}
