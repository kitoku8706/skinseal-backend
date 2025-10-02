package com.example.skin_back.intro.service;

import com.example.skin_back.intro.dto.IntroDTO;
import com.example.skin_back.intro.entity.IntroEntity;
import com.example.skin_back.intro.repository.IntroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntroServiceImpl implements IntroService {
    private final IntroRepository introRepository;

    @Override
    public IntroDTO createIntro(IntroDTO introDTO) {
        IntroEntity entity = IntroEntity.builder()
                .greeting(introDTO.getGreeting())
                .location(introDTO.getLocation())
                .menuType(introDTO.getMenuType())
                .build();
        IntroEntity saved = introRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public IntroDTO getIntroById(Long infoId) {
        return introRepository.findById(infoId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<IntroDTO> getAllIntros() {
        return introRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<IntroDTO> getIntrosByMenuType(String menuType) {
        return introRepository.findByMenuType(menuType).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IntroDTO updateIntro(Long infoId, IntroDTO introDTO) {
        return introRepository.findById(infoId)
                .map(entity -> {
                    entity.setGreeting(introDTO.getGreeting());
                    entity.setLocation(introDTO.getLocation());
                    entity.setMenuType(introDTO.getMenuType());
                    return toDTO(introRepository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public void deleteIntro(Long infoId) {
        introRepository.deleteById(infoId);
    }

    private IntroDTO toDTO(IntroEntity entity) {
        return IntroDTO.builder()
                .infoId(entity.getInfoId())
                .greeting(entity.getGreeting())
                .location(entity.getLocation())
                .menuType(entity.getMenuType())
                .build();
    }
}