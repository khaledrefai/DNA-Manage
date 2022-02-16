package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InbodyDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InbodyData.class);
        InbodyData inbodyData1 = new InbodyData();
        inbodyData1.setId(1L);
        InbodyData inbodyData2 = new InbodyData();
        inbodyData2.setId(inbodyData1.getId());
        assertThat(inbodyData1).isEqualTo(inbodyData2);
        inbodyData2.setId(2L);
        assertThat(inbodyData1).isNotEqualTo(inbodyData2);
        inbodyData1.setId(null);
        assertThat(inbodyData1).isNotEqualTo(inbodyData2);
    }
}
