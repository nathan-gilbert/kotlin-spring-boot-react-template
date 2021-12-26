package demo.baz.controller

import com.fasterxml.jackson.databind.ObjectMapper
import demo.baz.data.Baz
import demo.baz.data.BazRepository
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.transaction.Transactional

private const val CONTROLLER_PATH = "/baz"

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
internal class BazControllerTest @Autowired constructor(
  private val mvc: MockMvc,
  private val om: ObjectMapper,
  private val bazRepository: BazRepository
) {
  private companion object {
    const val DEFAULT_ID = 1L
    const val DEFAULT_BOO = "myBoo"
  }

  private lateinit var defaultBaz: Baz

  @BeforeEach
  fun setup() {
    defaultBaz = bazRepository.save(Baz(DEFAULT_ID, DEFAULT_BOO, null, null))
  }

  @Test
  fun `get a baz`() {
    lateinit var bazCreationResponse: BazResponse
    mvc.perform(
      get("$CONTROLLER_PATH/${defaultBaz.id}")
        .contentType(MediaType.APPLICATION_JSON)
    )
      .andExpect(status().isOk)
      .andExpect(jsonPath("\$.id").exists())
      .andExpect(jsonPath("\$.msg").exists())
      .andDo {
        bazCreationResponse = om.readValue(
          it.response.contentAsString,
          BazResponse::class.java
        )
      }
    assertThat(defaultBaz.id, equalTo(bazCreationResponse.id))
    assertThat(defaultBaz.msg, equalTo(bazCreationResponse.msg))
  }

  @Test
  fun `doesn't find a baz`() {
    mvc.perform(
      get("$CONTROLLER_PATH/5000")
        .contentType(MediaType.APPLICATION_JSON)
    )
      .andExpect(status().isNotFound)
  }

  @Test
  fun `creates a baz`() {
    val newBoo = "NewBoo"
    val bazBody = JSONObject(mapOf("msg" to newBoo)).toString()
    lateinit var bazCreationResponse: BazResponse
    mvc.perform(
      post(CONTROLLER_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(bazBody)
    )
      .andExpect(status().isCreated)
      .andExpect(jsonPath("\$.id").exists())
      .andExpect(jsonPath("\$.msg").exists())
      .andDo {
        bazCreationResponse = om.readValue(
          it.response.contentAsString,
          BazResponse::class.java
        )
      }

    val newBaz = bazRepository.findById(bazCreationResponse.id).get()
    assertThat(newBaz.id, equalTo(bazCreationResponse.id))
    assertThat(newBaz.msg, equalTo(bazCreationResponse.msg))
  }

  @Test
  fun `deletes a baz`() {
    mvc.perform(
      delete("$CONTROLLER_PATH/${defaultBaz.id}")
        .contentType(MediaType.APPLICATION_JSON)
    )
      .andExpect(status().isOk)

    val deletedBaz = bazRepository.findById(defaultBaz.id!!)
    assertThat(deletedBaz.isPresent, equalTo(false))
  }

  @Test
  fun `updates a baz`() {
    val updatedMsg = "NewMsg"
    val bazBody = JSONObject(mapOf("id" to defaultBaz.id!!, "msg" to updatedMsg)).toString()
    lateinit var bazUpdateResponse: BazResponse
    mvc.perform(
      put(CONTROLLER_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(bazBody)
    )
      .andExpect(status().isOk)
      .andExpect(jsonPath("\$.id").exists())
      .andExpect(jsonPath("\$.msg").exists())
      .andDo {
        bazUpdateResponse = om.readValue(
          it.response.contentAsString,
          BazResponse::class.java
        )
      }

    assertThat(defaultBaz.id, equalTo(bazUpdateResponse.id))
    assertThat(updatedMsg, equalTo(bazUpdateResponse.msg))
  }
}
