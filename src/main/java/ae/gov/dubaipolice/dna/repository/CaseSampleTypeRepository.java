package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.CaseSampleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaseSampleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseSampleTypeRepository extends JpaRepository<CaseSampleType, Long> {}
