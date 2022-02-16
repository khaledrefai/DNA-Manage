package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.CaseSample;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaseSample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseSampleRepository extends JpaRepository<CaseSample, Long> {}
