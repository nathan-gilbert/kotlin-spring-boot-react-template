package demo.baz.domain

interface BazService {
    /**
     * @param msg: message to save with baz
     */
    fun create(msg: String): BazDto

    /**
     * @param id: id of baz to retrieve
     */
    fun get(id: Long): BazDto

    /**
     * @param id: id of baz to update
     * @param msg: message to save with baz
     */
    fun update(id: Long, msg: String): BazDto

    /**
     * @param id: id of baz to remove
     */
    fun remove(id: Long)

    /**
     * @param id: id of baz to restore
     */
    fun restore(id: Long)
}
