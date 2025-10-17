package com.example.skin_back.appointment.service;

import com.example.skin_back.appointment.dto.AppointmentDTO;
import com.example.skin_back.appointment.entity.AppointmentEntity;
import com.example.skin_back.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // 날짜 유효성 검사 추가: appointmentDate가 null인 경우 예외 발생
        if (appointmentDTO.getAppointmentDate() == null) {
            throw new IllegalArgumentException("예약 날짜는 필수입니다. 날짜를 선택해주세요.");
        }

        // DTO를 Entity로 변환
        AppointmentEntity entity = AppointmentEntity.builder()
                .userId(appointmentDTO.getUserId())
                .appointmentDate(appointmentDTO.getAppointmentDate())
                .appointmentTime(appointmentDTO.getAppointmentTime())
                .purpose(appointmentDTO.getPurpose())
                .consultantId(appointmentDTO.getConsultantId())
                .build();

        // Entity 저장
        AppointmentEntity savedEntity = appointmentRepository.save(entity);

        // 저장된 Entity를 다시 DTO로 변환하여 반환
        return toDTO(savedEntity);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        // ID로 예약 엔티티를 찾고, 존재하면 DTO로 변환하여 반환. 없으면 null 반환.
        return appointmentRepository.findById(appointmentId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        // 모든 예약 엔티티를 조회하고, DTO 리스트로 변환하여 반환.
        return appointmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {
        // ID로 예약 엔티티를 찾고, 존재하면 업데이트.
        return appointmentRepository.findById(appointmentId)
                .map(entity -> {
                    // DTO의 값으로 엔티티 필드 업데이트
                    entity.setUserId(appointmentDTO.getUserId());
                    entity.setAppointmentDate(appointmentDTO.getAppointmentDate());
                    entity.setAppointmentTime(appointmentDTO.getAppointmentTime());
                    entity.setPurpose(appointmentDTO.getPurpose());
                    entity.setConsultantId(appointmentDTO.getConsultantId());
                    // 업데이트된 엔티티 저장 후 DTO로 변환하여 반환
                    return toDTO(appointmentRepository.save(entity));
                })
                .orElse(null); // 해당 ID의 엔티티가 없으면 null 반환
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        // ID로 예약 엔티티 삭제
        appointmentRepository.deleteById(appointmentId);
    }

    /**
     * AppointmentEntity를 AppointmentDTO로 변환하는 헬퍼 메소드.
     * 빌더 패턴을 사용하여 객체 생성.
     */
    private AppointmentDTO toDTO(AppointmentEntity entity) {
        return AppointmentDTO.builder()
                .appointmentId(entity.getAppointmentId())
                .userId(entity.getUserId())
                .appointmentDate(entity.getAppointmentDate())
                .appointmentTime(entity.getAppointmentTime())
                .purpose(entity.getPurpose())
                .consultantId(entity.getConsultantId())
                .build();
    }
}