package uz.tashkec.education.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TargetMapperTest {

    private TargetMapper targetMapper;

    @BeforeEach
    public void setUp() {
        targetMapper = new TargetMapperImpl();
    }
}
