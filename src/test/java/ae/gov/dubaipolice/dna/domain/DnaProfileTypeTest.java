package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DnaProfileTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DnaProfileType.class);
        DnaProfileType dnaProfileType1 = new DnaProfileType();
        dnaProfileType1.setId(1L);
        DnaProfileType dnaProfileType2 = new DnaProfileType();
        dnaProfileType2.setId(dnaProfileType1.getId());
        assertThat(dnaProfileType1).isEqualTo(dnaProfileType2);
        dnaProfileType2.setId(2L);
        assertThat(dnaProfileType1).isNotEqualTo(dnaProfileType2);
        dnaProfileType1.setId(null);
        assertThat(dnaProfileType1).isNotEqualTo(dnaProfileType2);
    }
}
