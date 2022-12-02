package uz.tashkec.education.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tashkec.education.domain.Participiant;
import uz.tashkec.education.repository.ParticipiantRepository;
import uz.tashkec.education.service.ParticipiantService;
import uz.tashkec.education.service.dto.ParticipiantDTO;
import uz.tashkec.education.service.mapper.ParticipiantMapper;

/**
 * Service Implementation for managing {@link Participiant}.
 */
@Service
@Transactional
public class ParticipiantServiceImpl implements ParticipiantService {

    private final Logger log = LoggerFactory.getLogger(ParticipiantServiceImpl.class);

    private final ParticipiantRepository participiantRepository;

    private final ParticipiantMapper participiantMapper;

    public ParticipiantServiceImpl(ParticipiantRepository participiantRepository, ParticipiantMapper participiantMapper) {
        this.participiantRepository = participiantRepository;
        this.participiantMapper = participiantMapper;
    }

    @Override
    public ParticipiantDTO save(ParticipiantDTO participiantDTO) {
        log.debug("Request to save Participiant : {}", participiantDTO);
        Participiant participiant = participiantMapper.toEntity(participiantDTO);
        participiant = participiantRepository.save(participiant);
        return participiantMapper.toDto(participiant);
    }

    @Override
    public ParticipiantDTO update(ParticipiantDTO participiantDTO) {
        log.debug("Request to update Participiant : {}", participiantDTO);
        Participiant participiant = participiantMapper.toEntity(participiantDTO);
        participiant = participiantRepository.save(participiant);
        return participiantMapper.toDto(participiant);
    }

    @Override
    public Optional<ParticipiantDTO> partialUpdate(ParticipiantDTO participiantDTO) {
        log.debug("Request to partially update Participiant : {}", participiantDTO);

        return participiantRepository
            .findById(participiantDTO.getId())
            .map(existingParticipiant -> {
                participiantMapper.partialUpdate(existingParticipiant, participiantDTO);

                return existingParticipiant;
            })
            .map(participiantRepository::save)
            .map(participiantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipiantDTO> findAll() {
        log.debug("Request to get all Participiants");
        return participiantRepository.findAll().stream().map(participiantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipiantDTO> findOne(Long id) {
        log.debug("Request to get Participiant : {}", id);
        return participiantRepository.findById(id).map(participiantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participiant : {}", id);
        participiantRepository.deleteById(id);
    }
}
