package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.ProjectType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {}
