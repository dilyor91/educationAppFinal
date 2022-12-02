package uz.tashkec.education.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParticipiantMapperTest {

    private ParticipiantMapper participiantMapper;

    @BeforeEach
    public void setUp() {
        participiantMapper = new ParticipiantMapperImpl();
    }
}
