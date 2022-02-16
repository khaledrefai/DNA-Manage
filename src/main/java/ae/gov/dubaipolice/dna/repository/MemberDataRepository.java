package ae.gov.dubaipolice.dna.repository;

import ae.gov.dubaipolice.dna.domain.MemberData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MemberData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberDataRepository extends JpaRepository<MemberData, Long> {}
