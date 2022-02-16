package ae.gov.dubaipolice.dna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ae.gov.dubaipolice.dna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberData.class);
        MemberData memberData1 = new MemberData();
        memberData1.setId(1L);
        MemberData memberData2 = new MemberData();
        memberData2.setId(memberData1.getId());
        assertThat(memberData1).isEqualTo(memberData2);
        memberData2.setId(2L);
        assertThat(memberData1).isNotEqualTo(memberData2);
        memberData1.setId(null);
        assertThat(memberData1).isNotEqualTo(memberData2);
    }
}
