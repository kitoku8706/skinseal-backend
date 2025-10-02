package com.example.skin_back.intro.controller;

import com.example.skin_back.intro.dto.IntroDTO;
import com.example.skin_back.intro.service.IntroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class IntroController {
    private final IntroService introService;

    @PostMapping
    public ResponseEntity<IntroDTO> create(@RequestBody IntroDTO dto) {
        return ResponseEntity.ok(introService.createIntro(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntroDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(introService.getIntroById(id));
    }

    @GetMapping
    public ResponseEntity<List<IntroDTO>> getAll() {
        return ResponseEntity.ok(introService.getAllIntros());
    }
    
    @GetMapping("/menu/{menuType}")
    public ResponseEntity<List<IntroDTO>> getByMenuType(@PathVariable String menuType) {
        return ResponseEntity.ok(introService.getIntrosByMenuType(menuType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntroDTO> update(@PathVariable Long id, @RequestBody IntroDTO dto) {
        return ResponseEntity.ok(introService.updateIntro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        introService.deleteIntro(id);
        return ResponseEntity.noContent().build();
    }
}