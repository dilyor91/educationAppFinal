package uz.tashkec.education.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tashkec.education.web.rest.TestUtil;

class PeriodDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodDTO.class);
        PeriodDTO periodDTO1 = new PeriodDTO();
        periodDTO1.setId(1L);
        PeriodDTO periodDTO2 = new PeriodDTO();
        assertThat(periodDTO1).isNotEqualTo(periodDTO2);
        periodDTO2.setId(periodDTO1.getId());
        assertThat(periodDTO1).isEqualTo(periodDTO2);
        periodDTO2.setId(2L);
        assertThat(periodDTO1).isNotEqualTo(periodDTO2);
        periodDTO1.setId(null);
        assertThat(periodDTO1).isNotEqualTo(periodDTO2);
    }
}
