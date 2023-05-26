package com.aueb.issues.web.technician;

import com.aueb.issues.model.entity.ApplicationEntity;
import com.aueb.issues.model.technician.ApplicationRequest;
import com.aueb.issues.model.technician.ApplicationResponse;
import com.aueb.issues.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    public ApplicationResponse response(){
        try{
             List<ApplicationEntity> issues = technicianRepository.findAll();

             return  ApplicationResponse.builder()
                     .issuesList(issues)
                     .build();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }

    }

    public ApplicationResponse update(String id, ApplicationRequest request){
        try{

            Optional<ApplicationEntity> issue = technicianRepository.findById(id);
            if(issue.isPresent()) {
                issue.get().setTitle(request.getTitle());

//                issue.get().setPriority(request.getPriority());
//                issue.get().setSites(request.getSites());
//                issue.get().setBuilding(request.getBuilding());
//                issue.get().setCreateDate(request.getCreateDate());
//                issue.get().setCompletionDate(request.getCompletionDate());
            }
            return ApplicationResponse.builder().issue(issue).build();

        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
}
