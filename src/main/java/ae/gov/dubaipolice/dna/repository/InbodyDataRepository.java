package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.InbodyData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InbodyData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InbodyDataRepository extends JpaRepository<InbodyData, Long> {}
