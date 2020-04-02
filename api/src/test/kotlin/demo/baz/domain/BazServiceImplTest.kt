package demo.baz.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import demo.baz.data.Baz
import demo.baz.data.BazMaster
import demo.baz.data.BazMasterRepository
import demo.baz.data.BazRepository
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.OffsetDateTime.now
import java.util.Optional

@ExtendWith(SpringExtension::class)
internal class BazServiceImplTest {

  @Mock
  private lateinit var bazRepository: BazRepository

  @Mock
  private lateinit var masterBazRepository: BazMasterRepository

  @Captor
  private lateinit var bazCaptor: ArgumentCaptor<Baz>

  @InjectMocks
  private lateinit var bazService: BazServiceImpl

  private companion object {
    const val DEFAULT_BOO = "myBoo"
    const val DEFAULT_ID = 1L
    const val DEFAULT_MSG = "myBooTwo"
    val DEFAULT_BAZ = Baz(2L, DEFAULT_MSG).apply { createdAt = now(); updatedAt = now() }
  }

  @Test
  fun create() {
    whenever(bazRepository.save(any<Baz>()))
        .thenReturn(DEFAULT_BAZ)

    val createdBazDto = bazService.create(DEFAULT_MSG)

    assertNotNull(createdBazDto.id)
    assertThat(createdBazDto.id, equalTo(DEFAULT_BAZ.id))
    assertThat(createdBazDto.msg, equalTo(DEFAULT_MSG))
    assertNotNull(createdBazDto.createdAt)
    assertNotNull(createdBazDto.updatedAt)

    verify(bazRepository).save(bazCaptor.capture())
    val savedBaz = bazCaptor.value
    assertThat(savedBaz.id, nullValue())
    assertThat(savedBaz.boo, equalTo(DEFAULT_MSG))
  }

  @Test
  fun restore() {
    val tempBaz = Baz(DEFAULT_ID, DEFAULT_BOO).apply { createdAt = now(); updatedAt = now() }
    val masterBaz = BazMaster(DEFAULT_ID, DEFAULT_BOO, tempBaz.createdAt, tempBaz.updatedAt, null)

    whenever(bazRepository.findById(DEFAULT_ID))
        .thenReturn(Optional.of(tempBaz))

    bazService.remove(DEFAULT_ID)

    whenever(masterBazRepository.findById(DEFAULT_ID))
        .thenReturn(Optional.of(masterBaz))

    bazService.restore(DEFAULT_ID)
    verify(bazRepository).restore(DEFAULT_ID)
  }
}
