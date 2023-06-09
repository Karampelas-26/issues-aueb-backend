package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.*;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.model.mapper.ApplicationMapper;
import com.aueb.issues.repository.ApplicationRepository;
import com.aueb.issues.repository.EquipmentRepository;
import com.aueb.issues.repository.SitesRepository;
import com.aueb.issues.repository.UserRepository;
import com.aueb.issues.web.dto.ApplicationDTO;
import com.aueb.issues.web.dto.CommentDTO;
import com.aueb.issues.web.dto.ResponseMessageDTO;
import com.aueb.issues.web.dto.TeacherApplicationsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class ApplicationService {
    private final String UN_ASSIGN_COMMAND="un_assign";
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SiteService siteService;
    @Autowired
    SitesRepository sitesRepository;
    @Autowired
    BuildingService buildingService;
    @Autowired
    EquipmentRepository equipmentRepository;

    public ResponseEntity<List<TeacherApplicationsDTO>> getTeacherApplications(UserEntity user){
        return getTeacherApplicationsByStatus(user, null);
    }
    public ResponseEntity<List<TeacherApplicationsDTO>> getTeacherApplicationsByStatus(UserEntity user, String status){
        List<TeacherApplicationsDTO> ret;
        try{
            if(status!=null && !(status.equals(Status.CREATED.name())||status.equals(Status.REJECTED.name())||
                    status.equals(Status.VALIDATED.name())||status.equals(Status.ASSIGNED.name())||status.equals(Status.COMPLETED.name())
                    ||status.equals(Status.ARCHIVED.name()))){
                log.error("Incorrect Status");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<ApplicationEntity> results = applicationRepository.findByValues(null,null,null,
                    status==null?null:Status.valueOf(status),null);
            List<ApplicationEntity> resFiltered=results.stream()
                    .filter(res-> !res.getStatus().equals(Status.ARCHIVED))
                    .filter(res->user.getPreferences().contains(res.getSite().getId()))
                    .toList();

            ret=resFiltered.stream().map(ApplicationMapper.INSTANCE::toTeacherDTO).toList();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }
    public ResponseEntity<ResponseMessageDTO> submitApplication(ObjectNode node, UserEntity user){
        try {
            String siteName = node.get("siteName").asText();
            String title =(node.get("title"))!=null?node.get("title").asText():null;
            SiteEntity site=sitesRepository.findSiteEntitiesByName(siteName).orElseThrow(() -> new EntityNotFoundException("Site with name: " + node.get("siteName").asText() + " not found"));
            IssueType issueType =(node.get("issueType"))!=null?IssueType.valueOf(node.get("issueType").asText()):null;
            Long equipmentId =(node.get("equipment"))!=null?node.get("equipment").asLong():null;
            if(equipmentId != null) {
                EquipmentEntity equipmentEntity=equipmentRepository.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment with id: " + equipmentId + " not found"));
            }
            ApplicationEntity newEntity=ApplicationEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .title(title)
                    .creationUser(user)
                    .createDate(LocalDateTime.now())
                    .status(Status.CREATED)
                    .priority(Priority.MEDIUM)
                    .site(site)
                    .issueType(issueType)
                    .build();
            applicationRepository.save(newEntity);
            if(user.getRole().equals(Role.TEACHER)){
                user.addPreference(site.getId());
            }
            return  ResponseEntity.ok(new ResponseMessageDTO("Successfully created new issue for site: " + site.getName()));
        } catch (EntityNotFoundException e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ResponseMessageDTO(e.getMessage()));
        }

    }

    public ResponseEntity<List<ApplicationDTO>> getAllApplications(UserEntity userEntity){
        return getApplicationsBySingleValues(userEntity,null,null,null,null,null);
    }
    public ResponseEntity<List<ApplicationDTO>> getApplicationsBySingleValues(UserEntity userEntity, String siteName, String priority, String issueType, String status, String buildingName){
        try {

            if(priority!=null && !(priority.equals(Priority.LOW.name())||priority.equals(Priority.HIGH.name())||priority.equals(Priority.MEDIUM.name()))){
                log.error("Incorrect Priority");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if(issueType!=null  && ! (issueType.equals(IssueType.CLIMATE_CONTROL.name())||issueType.equals(IssueType.ELECTRICAL.name())||
                    issueType.equals(IssueType.EQUIPMENT.name())||issueType.equals(IssueType.INFRASTRUCTURE.name()))){
                log.error("Incorrect IssueType");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(status!=null && !(status.equals(Status.CREATED.name())||status.equals(Status.REJECTED.name())||
                    status.equals(Status.VALIDATED.name())||status.equals(Status.ASSIGNED.name())||status.equals(Status.COMPLETED.name())
                    ||status.equals(Status.ARCHIVED.name()))){
                log.error("Incorrect Status");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Status> exStatus=new ArrayList<>();
            if(issueType==null&&userEntity.getRole().equals(Role.TECHNICIAN)&&userEntity.getTechnicalTeam()!=null){
                issueType=userEntity.getTechnicalTeam().name();
            }
            switch (userEntity.getRole()){
                case TECHNICIAN:
                    exStatus.add(Status.REJECTED);
                    exStatus.add(Status.CREATED);
                    exStatus.add(Status.ARCHIVED);
                    break;
                case COMMITTEE:
                    break;
                default:
                    exStatus.add(Status.ARCHIVED);
                    break;
            }

            List<ApplicationEntity> results = applicationRepository.findByValues(siteName,
                    priority==null?null:Priority.valueOf(priority),
                    issueType==null?null:IssueType.valueOf(issueType),
                    status==null?null:Status.valueOf(status),
                    buildingName);

            List<ApplicationEntity> resWithStatus = results.stream().filter(res -> !exStatus.contains(res.getStatus())).toList();
            return ResponseEntity.ok(toDTO(resWithStatus));
        }
        catch (Exception e){
            log.error(e.toString());
        }
        return ResponseEntity.internalServerError().body(null);
    }
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByBuilding(String buildingName){
        List<ApplicationEntity> entities=applicationRepository.findByBuildingName(buildingName);
        return ResponseEntity.ok(toDTO(entities));
    }

    public ResponseEntity<String> deleteIssue(String id){
        try{
            applicationRepository.findById(id).orElseThrow(()->{new EntityNotFoundException("No such entity found");
                return null;
            });
            applicationRepository.deleteById(id);
            return ResponseEntity.ok(null);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    public ResponseEntity<ApplicationDTO> updateApplication(ApplicationDTO applicationDTO){
        try{
            ApplicationEntity issue = applicationRepository.findById(applicationDTO.getId()).orElseThrow(()->
                {new EntityNotFoundException("Application not found"); return null;});

            issue.setDueDate(applicationDTO.getDueDate());
            issue.setPriority(Priority.valueOf(applicationDTO.getPriority()));
            issue.setDescription(applicationDTO.getDescription());
            issue.setTitle(applicationDTO.getTitle());
            issue.setIssueType(IssueType.valueOf(applicationDTO.getIssueType()));
            Status newStatus=Status.valueOf(applicationDTO.getStatus());
            String result =changeStatusCheck(issue.getStatus(),newStatus);
            if(result!=null){
                if(!result.equals(UN_ASSIGN_COMMAND)) {
                    log.error(result);
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }else {
                    issue.setIssueType(null);
                    issue.setAssigneeTech(null);
                }
            }
            issue.setStatus(newStatus);
            if(applicationDTO.getAssigneeTechId()!=null){
                Optional<UserEntity> newAssignee =userRepository.findById(applicationDTO.getAssigneeTechId());
                if(newAssignee.isPresent())
                    issue.setAssigneeTech(newAssignee.get());
                else {
                    log.error("Assignee not found in Database");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }else
                issue.setAssigneeTech(null);
            applicationRepository.save(issue);
            return ResponseEntity.ok(applicationDTO);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
    public ResponseEntity<ResponseMessageDTO> completeApplication(String id){
        try {
            ApplicationEntity issue = applicationRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Application not found")
            );
            String result = changeStatusCheck(issue.getStatus(), Status.COMPLETED);
            if (result != null) {
                log.error(result);
                return new ResponseEntity<>(new ResponseMessageDTO(result), HttpStatus.BAD_REQUEST);
            }
            issue.setStatus(Status.COMPLETED);
            issue.setCompletionDate(LocalDateTime.now());
            applicationRepository.save(issue);
            return ResponseEntity.ok(new ResponseMessageDTO("Completed"));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(new ResponseMessageDTO(e.getMessage()));
        }
    }

    //COMMENTS
    public  ResponseEntity<CommentDTO> createComment(String issueId, String comment, UserEntity user){
        try{
            ApplicationEntity issue = applicationRepository.findById(issueId).orElseThrow(()-> new EntityNotFoundException("Application not found"));
            List<CommentEntity> commentsOfIssue=issue.getComments();
//            CommentEntity newComment=ApplicationMapper.INSTANCE.toEntity(commentDTO);
            CommentEntity newComment = CommentEntity.builder()
                    .content(comment)
                    .dateTime(LocalDateTime.now())
                    .userName(user.getLastname() + " " + user.getFirstname())
                    .build();
            commentsOfIssue.add(newComment);
            applicationRepository.save(issue);
            return new ResponseEntity<>(ApplicationMapper.INSTANCE.toCommentDTO(newComment),HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //UTILITIES

    public ResponseEntity<Map<String,List<String>>> getStaticData(){
        Map<String,List<String>> ret=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        ret.put("IssueTypes",mapper.convertValue(IssueType.values(),List.class));
        ret.put("Status",mapper.convertValue(Status.values(),List.class));
        ret.put("Priotity",mapper.convertValue(Priority.values(), List.class));
        return ResponseEntity.ok(ret);

    }

    private String changeStatusCheck(Status current, Status status){
        switch (current) {
            case COMPLETED -> {
                if (status == Status.REJECTED || status == Status.CREATED)
                    return ("Invalid status change: Can't go back to creation or rejection" +
                            "from Completed");
                else return (UN_ASSIGN_COMMAND);
            }
            case ARCHIVED, REJECTED ->
            {
                return ("Invalid status change: Can't change status from Rejected or Archived");
            }
            default -> {
                return null;
            }
        }
    }

    public List<ApplicationDTO> toDTO(List<ApplicationEntity> entities){

        return entities.stream().map(ApplicationMapper.INSTANCE::toDTO).toList();
    }
}