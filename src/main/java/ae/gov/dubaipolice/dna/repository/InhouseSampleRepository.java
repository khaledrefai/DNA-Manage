package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.InhouseSample;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InhouseSample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InhouseSampleRepository extends JpaRepository<InhouseSample, Long> {}
