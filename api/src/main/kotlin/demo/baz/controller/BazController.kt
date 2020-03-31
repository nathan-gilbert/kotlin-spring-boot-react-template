package demo.baz.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class BazResponse(
    val qux: String
)

@RestController
class BazController {

  @GetMapping("/baz")
  fun health(): BazResponse {
    return BazResponse(qux = "Hack the planet!")
  }
}
