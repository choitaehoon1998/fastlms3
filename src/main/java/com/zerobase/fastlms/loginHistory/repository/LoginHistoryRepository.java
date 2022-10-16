package com.zerobase.fastlms.loginHistory.repository;

import com.zerobase.fastlms.loginHistory.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
