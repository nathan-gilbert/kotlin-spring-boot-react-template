package demo.baz.data

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Table

/**
 * Baz Entity.
 *
 * @property id - auto generated id
 * @property msg - msg of the baz
 */
@MappedSuperclass
class BaseBaz internal constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(name = "msg")
    val msg: String,
    @Column(name = "created_at")
    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,
    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null,
    @Column(name = "deleted_at")
    val deletedAt: OffsetDateTime? = null
)

/**
 * Soft deletable Baz
 */
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE baz SET deleted_at = now() WHERE id=?")
class Baz internal constructor(
    id: Long?,
    boo: String
) : BaseBaz(id, boo)

/**
 * Hard deletable Baz
 */
@Entity
@Table(name = "baz")
class BazMaster internal constructor(
    id: Long?,
    boo: String,
    createdAt: OffsetDateTime?,
    updatedAt: OffsetDateTime?,
    deletedAt: OffsetDateTime?
) : BaseBaz(id, boo, createdAt, updatedAt, deletedAt)
