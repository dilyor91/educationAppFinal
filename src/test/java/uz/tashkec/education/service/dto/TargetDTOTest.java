package uz.tashkec.education.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tashkec.education.web.rest.TestUtil;

class TargetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetDTO.class);
        TargetDTO targetDTO1 = new TargetDTO();
        targetDTO1.setId(1L);
        TargetDTO targetDTO2 = new TargetDTO();
        assertThat(targetDTO1).isNotEqualTo(targetDTO2);
        targetDTO2.setId(targetDTO1.getId());
        assertThat(targetDTO1).isEqualTo(targetDTO2);
        targetDTO2.setId(2L);
        assertThat(targetDTO1).isNotEqualTo(targetDTO2);
        targetDTO1.setId(null);
        assertThat(targetDTO1).isNotEqualTo(targetDTO2);
    }
}
