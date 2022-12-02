package uz.tashkec.education.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tashkec.education.web.rest.TestUtil;

class ParticipiantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipiantDTO.class);
        ParticipiantDTO participiantDTO1 = new ParticipiantDTO();
        participiantDTO1.setId(1L);
        ParticipiantDTO participiantDTO2 = new ParticipiantDTO();
        assertThat(participiantDTO1).isNotEqualTo(participiantDTO2);
        participiantDTO2.setId(participiantDTO1.getId());
        assertThat(participiantDTO1).isEqualTo(participiantDTO2);
        participiantDTO2.setId(2L);
        assertThat(participiantDTO1).isNotEqualTo(participiantDTO2);
        participiantDTO1.setId(null);
        assertThat(participiantDTO1).isNotEqualTo(participiantDTO2);
    }
}
