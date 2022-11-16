package app.repository.JPAEntity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "user")
@Entity
class UserDAO {
    @Id
    @Column(name = "uid", nullable = false)
    private var uid: String? = null

    @Column(name = "login", nullable = false)
    private var login: String,

    @Column(name = "pass", nullable = false)
    private var pass: String,

    @Column(name = "created", nullable = false)
    private var created: String,

    @Column(name = "role", nullable = false)
    private var role: String,

    @Column(name = "status", nullable = false)
    private var status: String


}