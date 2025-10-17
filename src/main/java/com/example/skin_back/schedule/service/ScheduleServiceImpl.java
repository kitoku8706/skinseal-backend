// 패키지명: com.example.skin_back.schedule.service.impl

package com.example.skin_back.schedule.service;

import com.example.skin_back.schedule.dto.ScheduleDTO;
import com.example.skin_back.schedule.entity.ScheduleEntity;
import com.example.skin_back.schedule.repository.ScheduleRepository;
//import com.example.skin_back.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        ScheduleEntity entity = toEntity(scheduleDTO);
        ScheduleEntity saved = scheduleRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    public ScheduleDTO getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<ScheduleDTO> getSchedulesByConsultantId(Integer consultantId) {
        return scheduleRepository.findByConsultantId(consultantId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {
        return scheduleRepository.findById(scheduleId)
                .map(entity -> {
                    entity.setConsultantId(scheduleDTO.getConsultantId());
                    entity.setWeekDay(scheduleDTO.getWeekDay());
                    entity.setTimeSlot(scheduleDTO.getTimeSlot());
                    entity.setIsAvailable(scheduleDTO.getIsAvailable());
                    entity.setNote(scheduleDTO.getNote());
                    ScheduleEntity updated = scheduleRepository.save(entity);
                    return toDTO(updated);
                }).orElse(null);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    private ScheduleDTO toDTO(ScheduleEntity entity) {
        return ScheduleDTO.builder()
                .scheduleId(entity.getScheduleId())
                .consultantId(entity.getConsultantId())
                .weekDay(entity.getWeekDay())
                .timeSlot(entity.getTimeSlot())
                .isAvailable(entity.getIsAvailable())
                .note(entity.getNote())
                .build();
    }

    private ScheduleEntity toEntity(ScheduleDTO dto) {
        return ScheduleEntity.builder()
                .scheduleId(dto.getScheduleId())
                .consultantId(dto.getConsultantId())
                .weekDay(dto.getWeekDay())
                .timeSlot(dto.getTimeSlot())
                .isAvailable(dto.getIsAvailable())
                .note(dto.getNote())
                .build();
    }
}