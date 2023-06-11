package com.aueb.issues.web;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.OTPRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class NightScheduler {
    @Autowired
    OTPRepository otpRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 * * * * *")
    public void runOverNight(){
        //clearOTP();
        log.info("scheduler run" );
        archiveApplications();
    }

    private void clearOTP(){
        otpRepository.deleteAll();
    }
    private void archiveApplications(){
        List<ApplicationEntity> completedApps = applicationRepository.findByValues(null,null,null, Status.COMPLETED,null);
        for(ApplicationEntity app:completedApps){
            if(moreThan24Hours(LocalDateTime.now(),app.getCompletionDate()))
                app.setStatus(Status.ARCHIVED);
        }
        applicationRepository.saveAll(completedApps);
    }

    private boolean moreThan24Hours(LocalDateTime dateTime1,LocalDateTime dateTime2){
        Duration duration = Duration.between(dateTime1, dateTime2);
        return duration.toHours() > 24;
    }
}
