package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaseSampleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseSample.class);
        CaseSample caseSample1 = new CaseSample();
        caseSample1.setId(1L);
        CaseSample caseSample2 = new CaseSample();
        caseSample2.setId(caseSample1.getId());
        assertThat(caseSample1).isEqualTo(caseSample2);
        caseSample2.setId(2L);
        assertThat(caseSample1).isNotEqualTo(caseSample2);
        caseSample1.setId(null);
        assertThat(caseSample1).isNotEqualTo(caseSample2);
    }
}
