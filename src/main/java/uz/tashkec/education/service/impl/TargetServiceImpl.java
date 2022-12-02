package uz.tashkec.education.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tashkec.education.domain.Target;
import uz.tashkec.education.repository.TargetRepository;
import uz.tashkec.education.service.TargetService;
import uz.tashkec.education.service.dto.TargetDTO;
import uz.tashkec.education.service.mapper.TargetMapper;

/**
 * Service Implementation for managing {@link Target}.
 */
@Service
@Transactional
public class TargetServiceImpl implements TargetService {

    private final Logger log = LoggerFactory.getLogger(TargetServiceImpl.class);

    private final TargetRepository targetRepository;

    private final TargetMapper targetMapper;

    public TargetServiceImpl(TargetRepository targetRepository, TargetMapper targetMapper) {
        this.targetRepository = targetRepository;
        this.targetMapper = targetMapper;
    }

    @Override
    public TargetDTO save(TargetDTO targetDTO) {
        log.debug("Request to save Target : {}", targetDTO);
        Target target = targetMapper.toEntity(targetDTO);
        target = targetRepository.save(target);
        return targetMapper.toDto(target);
    }

    @Override
    public TargetDTO update(TargetDTO targetDTO) {
        log.debug("Request to update Target : {}", targetDTO);
        Target target = targetMapper.toEntity(targetDTO);
        target = targetRepository.save(target);
        return targetMapper.toDto(target);
    }

    @Override
    public Optional<TargetDTO> partialUpdate(TargetDTO targetDTO) {
        log.debug("Request to partially update Target : {}", targetDTO);

        return targetRepository
            .findById(targetDTO.getId())
            .map(existingTarget -> {
                targetMapper.partialUpdate(existingTarget, targetDTO);

                return existingTarget;
            })
            .map(targetRepository::save)
            .map(targetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TargetDTO> findAll() {
        log.debug("Request to get all Targets");
        return targetRepository.findAll().stream().map(targetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TargetDTO> findOne(Long id) {
        log.debug("Request to get Target : {}", id);
        return targetRepository.findById(id).map(targetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Target : {}", id);
        targetRepository.deleteById(id);
    }
}
