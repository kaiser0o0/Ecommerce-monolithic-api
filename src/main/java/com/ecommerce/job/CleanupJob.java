package com.ecommerce.job;

import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CleanupJob {

    private final UserRepository userRepository;

    // Cronjob: Her gece 03:00'da inaktif hesapları temizler (Hard Delete)
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void hardDeleteInactiveUsers() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(2);
        userRepository.deleteByIsDeletedTrueAndDeletedAtBefore(thresholdDate);
    }
}