package com.example.skin_back.check.service;

import com.example.skin_back.check.entity.CheckEntity;

public interface CheckService {
    CheckEntity getLatestCheck(Long userId);
    void cancelCheck(Long appointmentId);
}
