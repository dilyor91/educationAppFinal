package uz.tashkec.education.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tashkec.education.domain.Period;
import uz.tashkec.education.repository.PeriodRepository;
import uz.tashkec.education.service.PeriodService;
import uz.tashkec.education.service.dto.PeriodDTO;
import uz.tashkec.education.service.mapper.PeriodMapper;

/**
 * Service Implementation for managing {@link Period}.
 */
@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    public PeriodServiceImpl(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    @Override
    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return periodMapper.toDto(period);
    }

    @Override
    public PeriodDTO update(PeriodDTO periodDTO) {
        log.debug("Request to update Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return periodMapper.toDto(period);
    }

    @Override
    public Optional<PeriodDTO> partialUpdate(PeriodDTO periodDTO) {
        log.debug("Request to partially update Period : {}", periodDTO);

        return periodRepository
            .findById(periodDTO.getId())
            .map(existingPeriod -> {
                periodMapper.partialUpdate(existingPeriod, periodDTO);

                return existingPeriod;
            })
            .map(periodRepository::save)
            .map(periodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periods");
        return periodRepository.findAll(pageable).map(periodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodDTO> findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        return periodRepository.findById(id).map(periodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.deleteById(id);
    }
}
