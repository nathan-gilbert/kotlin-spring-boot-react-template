package demo.baz.controller

import demo.baz.domain.BazService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

data class CreateBazRequest(
    val boo: String
)

data class BazResponse(
    val id: Long,
    val qux: String
)

@RestController
class BazController(
    private val bazService: BazService
) {

  @GetMapping("/baz")
  fun getBaz(): BazResponse {
    return BazResponse(1L, qux = "Hack the planet!")
  }

  @PostMapping("/baz")
  @ResponseStatus(HttpStatus.CREATED)
  fun createBaz(
      @Validated @RequestBody createBazBody: CreateBazRequest
  ): BazResponse {
    val newBaz = bazService.create(createBazBody.boo)
    return BazResponse(newBaz.id!!, newBaz.msg)
  }
}
