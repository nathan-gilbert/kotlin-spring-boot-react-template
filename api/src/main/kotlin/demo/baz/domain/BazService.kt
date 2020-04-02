package demo.baz.domain

interface BazService {
  /**
   * @param msg: message to save with baz
   */
  fun create(msg: String): BazDto
  fun get(id: Long): BazDto
  fun remove(id: Long)
  fun restore(id: Long)
}
