package demo.baz.controller

import demo.baz.domain.BazService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

data class CreateBazRequest(
    val msg: String
)

data class UpdateBazRequest(
    val id: Long,
    val msg: String
)

data class BazResponse(
    val id: Long,
    val msg: String,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?,
    val deletedAt: OffsetDateTime?
)

@RestController
@RequestMapping("/baz")
class BazController(
    private val bazService: BazService
) {
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun getBaz(@PathVariable id: Long): BazResponse {
    val result = bazService.get(id)
    return BazResponse(result.id!!, result.msg, result.createdAt, result.updatedAt, null)
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun deleteBaz(@PathVariable id: Long) {
    bazService.remove(id)
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createBaz(
      @Validated @RequestBody createBazBody: CreateBazRequest
  ): BazResponse {
    val newBaz = bazService.create(createBazBody.msg)
    return BazResponse(newBaz.id!!, newBaz.msg, newBaz.createdAt, newBaz.updatedAt, newBaz.deletedAt)
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  fun updateBaz(
      @Validated @RequestBody updateBazBody: UpdateBazRequest
  ): BazResponse {
    bazService.update(updateBazBody.id, updateBazBody.msg)
    val updatedBaz = bazService.get(updateBazBody.id)
    return BazResponse(updatedBaz.id!!,
        updatedBaz.msg,
        updatedBaz.createdAt,
        updatedBaz.updatedAt,
        updatedBaz.deletedAt)
  }
}
