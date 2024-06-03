package km.self.learningkotlin.repository

import km.self.learningkotlin.entity.ApplicationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface ApplicationRepository : JpaRepository<ApplicationEntity, Long?>
