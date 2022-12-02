package uz.tashkec.education.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tashkec.education.web.rest.TestUtil;

class ApplicationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationDTO.class);
        ApplicationDTO applicationDTO1 = new ApplicationDTO();
        applicationDTO1.setId(1L);
        ApplicationDTO applicationDTO2 = new ApplicationDTO();
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO2.setId(applicationDTO1.getId());
        assertThat(applicationDTO1).isEqualTo(applicationDTO2);
        applicationDTO2.setId(2L);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
        applicationDTO1.setId(null);
        assertThat(applicationDTO1).isNotEqualTo(applicationDTO2);
    }
}
