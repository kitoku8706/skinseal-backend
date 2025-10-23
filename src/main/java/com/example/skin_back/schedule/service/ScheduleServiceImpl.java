package com.example.skin_back.schedule.service;

import com.example.skin_back.schedule.dto.ScheduleDTO;
import com.example.skin_back.schedule.entity.Schedule;
import com.example.skin_back.schedule.repository.ScheduleRepository;
import com.example.skin_back.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getSchedulesByDate(String date) {
        return scheduleRepository.findByDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getSchedulesByCounselorId(Long counselorId) {
        return scheduleRepository.findByCounselorId(counselorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getSchedulesByCounselorIdAndDate(Long counselorId, String date) {
        return scheduleRepository.findByCounselorIdAndDate(counselorId, date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getSchedulesByCounselorName(String counselorName) {
        return scheduleRepository.findByCounselor_name(counselorName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getSchedulesByCounselorNameAndDate(String counselorName, String date) {
        return scheduleRepository.findByCounselor_nameAndDate(counselorName, date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
        return convertToDTO(schedule);
    }

    @Override
    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(savedSchedule);
    }

    @Override
    @Transactional
    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
        
        existingSchedule.setAvailable(scheduleDTO.getAvailable());
        existingSchedule.setCounselor_name(scheduleDTO.getCounselor_name());
        existingSchedule.setDate(scheduleDTO.getDate());
        existingSchedule.setTime(scheduleDTO.getTime());
        existingSchedule.setCounselorId(scheduleDTO.getCounselorId());
        
        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return convertToDTO(updatedSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateAvailability(Long id, Boolean available) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));
        schedule.setAvailable(available);
        scheduleRepository.save(schedule);
    }

    // Entity를 DTO로 변환
    private ScheduleDTO convertToDTO(Schedule schedule) {
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .available(schedule.getAvailable())
                .counselor_name(schedule.getCounselor_name())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .counselorId(schedule.getCounselorId())
                .build();
    }

    // DTO를 Entity로 변환
    private Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setAvailable(scheduleDTO.getAvailable());
        schedule.setCounselor_name(scheduleDTO.getCounselor_name());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setTime(scheduleDTO.getTime());
        schedule.setCounselorId(scheduleDTO.getCounselorId());
        return schedule;
    }
}