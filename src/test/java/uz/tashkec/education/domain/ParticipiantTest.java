package uz.tashkec.education.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tashkec.education.web.rest.TestUtil;

class ParticipiantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participiant.class);
        Participiant participiant1 = new Participiant();
        participiant1.setId(1L);
        Participiant participiant2 = new Participiant();
        participiant2.setId(participiant1.getId());
        assertThat(participiant1).isEqualTo(participiant2);
        participiant2.setId(2L);
        assertThat(participiant1).isNotEqualTo(participiant2);
        participiant1.setId(null);
        assertThat(participiant1).isNotEqualTo(participiant2);
    }
}
