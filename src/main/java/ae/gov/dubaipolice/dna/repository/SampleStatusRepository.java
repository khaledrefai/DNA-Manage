package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.SampleStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SampleStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleStatusRepository extends JpaRepository<SampleStatus, Long> {}
