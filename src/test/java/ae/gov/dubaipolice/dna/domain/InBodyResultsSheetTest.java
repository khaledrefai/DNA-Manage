package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InBodyResultsSheetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InBodyResultsSheet.class);
        InBodyResultsSheet inBodyResultsSheet1 = new InBodyResultsSheet();
        inBodyResultsSheet1.setId(1L);
        InBodyResultsSheet inBodyResultsSheet2 = new InBodyResultsSheet();
        inBodyResultsSheet2.setId(inBodyResultsSheet1.getId());
        assertThat(inBodyResultsSheet1).isEqualTo(inBodyResultsSheet2);
        inBodyResultsSheet2.setId(2L);
        assertThat(inBodyResultsSheet1).isNotEqualTo(inBodyResultsSheet2);
        inBodyResultsSheet1.setId(null);
        assertThat(inBodyResultsSheet1).isNotEqualTo(inBodyResultsSheet2);
    }
}
