package com.zerobase.fastlms.loginHistory.repository;

import com.zerobase.fastlms.loginHistory.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    Optional<List<LoginHistory>> findByUserId(String userId);
}
