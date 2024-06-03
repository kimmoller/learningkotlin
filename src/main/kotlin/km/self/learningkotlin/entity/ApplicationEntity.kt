package km.self.learningkotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "Application")
data class ApplicationEntity(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
  val name: String
)
