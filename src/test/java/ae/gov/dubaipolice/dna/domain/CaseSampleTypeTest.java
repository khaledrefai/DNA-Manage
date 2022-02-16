package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaseSampleTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseSampleType.class);
        CaseSampleType caseSampleType1 = new CaseSampleType();
        caseSampleType1.setId(1L);
        CaseSampleType caseSampleType2 = new CaseSampleType();
        caseSampleType2.setId(caseSampleType1.getId());
        assertThat(caseSampleType1).isEqualTo(caseSampleType2);
        caseSampleType2.setId(2L);
        assertThat(caseSampleType1).isNotEqualTo(caseSampleType2);
        caseSampleType1.setId(null);
        assertThat(caseSampleType1).isNotEqualTo(caseSampleType2);
    }
}
