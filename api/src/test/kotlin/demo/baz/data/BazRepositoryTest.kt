package demo.baz.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class BazRepositoryTest @Autowired constructor(
  private val bazRepository: BazRepository
) {

  val baz: Baz by lazy {
    bazRepository.save(Baz(null, "myBaz", null, null))
  }

  @Test
  fun `findById find a baz`() {
    val foundBaz = bazRepository.findById(baz.id!!)
    assertThat(foundBaz).isPresent
    assertThat(foundBaz.get().id).isEqualTo(baz.id!!)
    assertThat(foundBaz.get().msg).isEqualTo(baz.msg)
    assertThat(foundBaz.get().createdAt).isEqualTo(baz.createdAt)
    assertThat(foundBaz.get().updatedAt).isEqualTo(baz.updatedAt)
    assertThat(foundBaz.get().deletedAt).isEqualTo(baz.deletedAt)
  }
}

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class BazMasterRepositoryTest @Autowired constructor(
  private val masterBazRepository: BazMasterRepository,
  private val bazRepository: BazRepository
) {

  val bazMaster: BazMaster by lazy {
    masterBazRepository.save(BazMaster(null, "myBaz", null, null, null))
  }

  val anotherBazMaster: BazMaster by lazy {
    masterBazRepository.save(BazMaster(null, "myBaz2", null, null, null))
  }

  @Test
  fun `findById find a master baz`() {
    val foundBaz = bazRepository.findById(bazMaster.id!!)
    assertThat(foundBaz).isPresent
    assertThat(foundBaz.get().id).isEqualTo(bazMaster.id!!)
    assertThat(foundBaz.get().msg).isEqualTo(bazMaster.msg)
    assertThat(foundBaz.get().createdAt).isEqualTo(bazMaster.createdAt)
    assertThat(foundBaz.get().updatedAt).isEqualTo(bazMaster.updatedAt)
    assertThat(foundBaz.get().deletedAt).isEqualTo(bazMaster.deletedAt)
  }

  @Test
  fun `findById should find a soft deleted entity`() {
    assertThat(bazRepository.findById(anotherBazMaster.id!!)).isPresent
    bazRepository.deleteById(anotherBazMaster.id!!)
    assertThat(masterBazRepository.findById(anotherBazMaster.id!!)).isPresent
  }
}
