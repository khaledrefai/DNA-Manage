package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SampleTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleType.class);
        SampleType sampleType1 = new SampleType();
        sampleType1.setId(1L);
        SampleType sampleType2 = new SampleType();
        sampleType2.setId(sampleType1.getId());
        assertThat(sampleType1).isEqualTo(sampleType2);
        sampleType2.setId(2L);
        assertThat(sampleType1).isNotEqualTo(sampleType2);
        sampleType1.setId(null);
        assertThat(sampleType1).isNotEqualTo(sampleType2);
    }
}
