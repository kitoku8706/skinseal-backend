package com.example.skin_back.timetable.service;

import com.example.skin_back.timetable.dto.TimeTableWeekDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeTableServiceImpl implements TimeTableService {

    private final EntityManager em;

    @Override
    public List<TimeTableWeekDto> getTimeTableByWeek(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(4); // 월~금 기준

        // ✅ 실제 appointment 테이블에서 가져오기
        String sql = """
            SELECT 
                a.counselor_id,
                a.appointment_date,
                a.appointment_time,
                a.status
            FROM appointment a
            WHERE a.appointment_date BETWEEN :startDate AND :endDate
        """;

        Query query = em.createNativeQuery(sql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Object[]> results = query.getResultList();

        return results.stream().map(row -> {
            TimeTableWeekDto dto = new TimeTableWeekDto();
            dto.setCounselorId(((Number) row[0]).longValue());
            dto.setTimetableDate(String.valueOf(row[1]));
            dto.setTimetableTime((String) row[2]);
            dto.setStatus((String) row[3]);
            return dto;
        }).collect(Collectors.toList());
    }
}
