package demo.baz.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

/**
 * Repository for soft deletable Bazes
 */
interface BazRepository : JpaRepository<Baz, Long>, JpaSpecificationExecutor<Baz> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE baz SET deleted_at = NULL WHERE id=?1", nativeQuery = true)
    fun restore(id: Long)
}

/**
 * Repository for hard deletable Bazes
 */
interface BazMasterRepository : JpaRepository<BazMaster, Long>
