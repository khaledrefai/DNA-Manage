package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkPlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkPlace.class);
        WorkPlace workPlace1 = new WorkPlace();
        workPlace1.setId(1L);
        WorkPlace workPlace2 = new WorkPlace();
        workPlace2.setId(workPlace1.getId());
        assertThat(workPlace1).isEqualTo(workPlace2);
        workPlace2.setId(2L);
        assertThat(workPlace1).isNotEqualTo(workPlace2);
        workPlace1.setId(null);
        assertThat(workPlace1).isNotEqualTo(workPlace2);
    }
}
