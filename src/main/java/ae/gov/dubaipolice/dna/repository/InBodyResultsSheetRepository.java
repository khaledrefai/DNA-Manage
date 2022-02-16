package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.InBodyResultsSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InBodyResultsSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InBodyResultsSheetRepository extends JpaRepository<InBodyResultsSheet, Long> {}
