package com.example.skin_back.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Long id;
    private Boolean available;
    private String counselor_name;
    private String date;
    private String time;
    private Long counselorId;
    
    // 직접 getter 추가
    public Long getCounselorId() {
        return this.counselorId;
    }
}