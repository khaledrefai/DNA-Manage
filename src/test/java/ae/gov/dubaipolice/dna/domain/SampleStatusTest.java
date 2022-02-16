package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SampleStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SampleStatus.class);
        SampleStatus sampleStatus1 = new SampleStatus();
        sampleStatus1.setId(1L);
        SampleStatus sampleStatus2 = new SampleStatus();
        sampleStatus2.setId(sampleStatus1.getId());
        assertThat(sampleStatus1).isEqualTo(sampleStatus2);
        sampleStatus2.setId(2L);
        assertThat(sampleStatus1).isNotEqualTo(sampleStatus2);
        sampleStatus1.setId(null);
        assertThat(sampleStatus1).isNotEqualTo(sampleStatus2);
    }
}
