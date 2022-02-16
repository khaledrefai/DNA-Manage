package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InhouseSampleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InhouseSample.class);
        InhouseSample inhouseSample1 = new InhouseSample();
        inhouseSample1.setId(1L);
        InhouseSample inhouseSample2 = new InhouseSample();
        inhouseSample2.setId(inhouseSample1.getId());
        assertThat(inhouseSample1).isEqualTo(inhouseSample2);
        inhouseSample2.setId(2L);
        assertThat(inhouseSample1).isNotEqualTo(inhouseSample2);
        inhouseSample1.setId(null);
        assertThat(inhouseSample1).isNotEqualTo(inhouseSample2);
    }
}
