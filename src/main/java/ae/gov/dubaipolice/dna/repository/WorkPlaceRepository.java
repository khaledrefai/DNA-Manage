package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.WorkPlace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long> {}
