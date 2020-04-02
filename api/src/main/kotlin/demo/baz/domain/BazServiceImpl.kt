package demo.baz.domain

import com.fasterxml.jackson.annotation.JsonFormat
import demo.baz.data.Baz
import demo.baz.data.BazRepository
import demo.baz.validation.RecordNotFoundException
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

data class BazDto(
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val id: Long?,
    val msg: String,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

@Service
class BazServiceImpl(
    private val bazRepository: BazRepository
) : BazService {

  override fun create(msg: String): BazDto {
    val newBaz = bazRepository.save(Baz(null, msg))
    return newBaz.toDto()
  }

  override fun get(id: Long): BazDto {
    val baz = bazRepository.findById(id)
        .orElseThrow { RecordNotFoundException("Invalid baz id: $id"); }
    return baz.toDto()
  }

  override fun remove(id: Long) {
    bazRepository.deleteById(id)
  }

  override fun restore(id: Long) {
    bazRepository.restore(id)
  }

  private fun Baz.toDto() =
      BazDto(
          id,
          boo,
          createdAt,
          updatedAt
      )
}
