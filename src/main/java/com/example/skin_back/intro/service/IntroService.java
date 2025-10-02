package com.example.skin_back.intro.service;

import com.example.skin_back.intro.dto.IntroDTO;
import java.util.List;

public interface IntroService {
    IntroDTO createIntro(IntroDTO introDTO);
    IntroDTO getIntroById(Long infoId);
    List<IntroDTO> getAllIntros();
    List<IntroDTO> getIntrosByMenuType(String menuType);
    IntroDTO updateIntro(Long infoId, IntroDTO introDTO);
    void deleteIntro(Long infoId);
}