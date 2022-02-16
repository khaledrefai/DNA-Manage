package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.DnaProfileType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DnaProfileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DnaProfileTypeRepository extends JpaRepository<DnaProfileType, Long> {}
