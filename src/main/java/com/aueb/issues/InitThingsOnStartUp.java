package com.aueb.issues;

import com.aueb.issues.model.entity.*;
import com.aueb.issues.model.enums.IssueType;
import com.aueb.issues.model.enums.Priority;
import com.aueb.issues.model.enums.Role;
import com.aueb.issues.model.enums.Status;
import com.aueb.issues.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author George Karampelasd
 */

@Configuration
@Component
@Order(1)
@RequiredArgsConstructor
public class InitThingsOnStartUp implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private SitesRepository sitesRepository;
    LocalDateTime date = LocalDateTime.now();
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void run(String... args) throws Exception {
//        BuildingEntity marsaleio = BuildingEntity.builder()
//                .id(0)
//                .name("marsaleio megaro")
//                .address("28hs oktombriou")
//                .floors(5)
//                .build();
//        buildingRepository.save(marsaleio);
//        SiteEntity a32 =  SiteEntity.builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .name("A 32")
//                .buildingId(String.valueOf(marsaleio.getId()))
//                .floor("3")
//                .build();
//        sitesRepository.save(a32);
//        ApplicationEntity issue1 = ApplicationEntity.builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .title("Progector error")
//                .site(a32)
//                .building(marsaleio)
//                .priority(Priority.MEDIUM)
//                .createDate(date)
//                .completionDate(date.plusDays(3))
//                .build();
//        applicationRepository.save(issue1);
//
//        ApplicationEntity issue2 = ApplicationEntity.builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .title("HDMI error")
//                .site(a32)
//                .building(marsaleio)
//                .priority(Priority.LOW)
//                .createDate(date)
//                .completionDate(date.plusDays(4))
//                .build();
//        applicationRepository.save(issue2);


        UserEntity admin = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .email("george.karampelas.26@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227237")
                .firstname("George")
                .lastname("Karampelas")
                .gender("M")
                .address("Eygeniou Karavias 12, 11144 Attica")
                .createdDate(getRandomDate())
                .role(Role.COMMITTEE)
                .activated(true)
                .build();
        userRepository.save(admin);
        UserEntity teacher = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .email("giorgosmeid@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .phone("6945227238")
                .firstname("Steve")
                .lastname("iDanys")
                .gender("M")
                .address("Eygeniou Karavias 2, 11144 Attica")
                .createdDate(getRandomDate())
                .role(Role.TEACHER)
                .activated(true)
                .build();

        UserEntity tech = UserEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .password(passwordEncoder.encode("pass"))
                .email("haralabos13aggelis@hotmail.com")
                .phone("6945227238")
                .firstname("Charalampos")
                .lastname("Aggelis")
                .gender("M")
                .address("Eygeniou Karavias 32, 11144 Attica")
                .createdDate(getRandomDate())
                .role(Role.TECHNICIAN)
                .technicalTeam(IssueType.ELECTRICAL)
                .activated(true)
                .build();
        userRepository.save(tech);


        createBuildins();
        createApplications(10000);
        HashSet<Long> pref= new HashSet<>();
        pref.add(1L);
        pref.add(2L);
        pref.add(3L);
        teacher.setPreferences(pref);
        userRepository.save(teacher);
    }

    private LocalDateTime getRandomDate(){
        Random random = new Random();

        // Generate a random year between 2020 and 2023
        int year = random.nextInt(2024 - 2020) + 2020;

        // Generate a random month between 1 and 12
        int month = random.nextInt(12) + 1;

        // Generate a random day between 1 and 28 (assuming non-leap year)
        int day = random.nextInt(28) + 1;

        // Generate a random hour between 0 and 23
        int hour = random.nextInt(24);

        // Generate a random minute between 0 and 59
        int minute = random.nextInt(60);

        // Generate a random second between 0 and 59
        int second = random.nextInt(60);

        // Generate a random nanosecond between 0 and 999_999_999
        int nano = random.nextInt(1_000_000_000);

        // Create the LocalDateTime object using the generated values
        return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    private void createBuildins(){
        BuildingEntity[] builds = new BuildingEntity[10];
        builds[0] = BuildingEntity.builder().name("Πατησίων 76").address("28ης Οκτωβρίου 76, Αθήνα 104 34").floors(5).build();
        builds[1] = BuildingEntity.builder().name("Πατησίων 80").address("28ης Οκτωβρίου 80, Αθήνα 104 34").floors(6).build();
        builds[2] = BuildingEntity.builder().name("Πατησίων 95").address("28ης Οκτωβρίου 95, Αθήνα 104 34").floors(4).build();
        builds[3] = BuildingEntity.builder().name("Δεριγνύ 12").address("Δεριγνύ 12, Αθήνα 104 34").floors(4).build();
        builds[4] = BuildingEntity.builder().name("Κοδριγκτώνος 12").address("Κοδριγκτώνος 12, Αθήνα 112 57").floors(4).build();
        builds[5] = BuildingEntity.builder().name("Ελπίδος 13").address("Ελπίδος 13, Αθήνα 104 34").floors(4).build();
        builds[6] = BuildingEntity.builder().name("Κεφαλληνίας 46").address("Κεφαλληνίας 46, Αθήνα 112 51").floors(4).build();
        builds[7] = BuildingEntity.builder().name("Τροίας 2, Κιμώλου και Σπετσών").address("Τροίας 2, Αθήνα 113 62").floors(4).build();
        builds[8] = BuildingEntity.builder().name("Ευελπίδων 29").address("Ευελπίδων 29, Αθήνα 113 62").floors(4).build();
        builds[9] = BuildingEntity.builder().name("Ευελπίδων 47Α και Λευκάδος 33").address("Λευκάδος 33, Αθήνα 113 62").floors(4).build();

        for(BuildingEntity b: builds) buildingRepository.save(b);

        List<BuildingEntity> buildings = buildingRepository.findAll();

        EquipmentEntity[] equipmentEntities = new EquipmentEntity[8];
        equipmentEntities[0] = EquipmentEntity.builder().typeOfEquipment("Προτζεκτορας").build();
        equipmentEntities[1] = EquipmentEntity.builder().typeOfEquipment("Πίνακας").build();
        equipmentEntities[2] = EquipmentEntity.builder().typeOfEquipment("Ethernet").build();
        equipmentEntities[3] = EquipmentEntity.builder().typeOfEquipment("Wifi").build();
        equipmentEntities[4] = EquipmentEntity.builder().typeOfEquipment("HDMI").build();
        equipmentEntities[5] = EquipmentEntity.builder().typeOfEquipment("Πόρτα").build();
        equipmentEntities[6] = EquipmentEntity.builder().typeOfEquipment("Κλιματιστικό").build();
        equipmentEntities[7] = EquipmentEntity.builder().typeOfEquipment("Παράθυρα").build();

        for(EquipmentEntity equipment: equipmentEntities){
            equipmentRepository.save(equipment);
        }
        Random random = new Random();
        int prefix = 0;
        for(BuildingEntity b: buildings){
            prefix++;
            for(int i=0; i<=5;i++){
                for(int j = 1; j <= b.getFloors(); j++){
                    SiteEntity site = SiteEntity.builder().name(prefix + b.getName().substring(0,1)+String.valueOf(j)+String.valueOf(i)).floor(String.valueOf(j)).building(b).build();
//                    site.addEquipment(equipmentEntities[random.nextInt(8)]);
                    for(EquipmentEntity equipment: equipmentEntities){
                        site.addEquipment(equipment);
                    }
                    sitesRepository.save(site);
//                    b.addSite(site);
                }
            }
            buildingRepository.save(b);
        }


    }


    @Transactional
    public void createApplications(int num) {

        long appNum = 0;
        Random random = new Random();
        List<UserEntity> users = userRepository.findAll();
        List<SiteEntity> sites = sitesRepository.findAll();
        List<EquipmentEntity> equipments = equipmentRepository.findAll();
        Priority[] priorities = Priority.values();
        Status[] statuses = Status.values();
        for(int i=0; i<num;i++){
            SiteEntity site = sites.get(random.nextInt(sites.size()));
            ApplicationEntity app = ApplicationEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .title("Application: " + String.valueOf(appNum))
                    .site(site)
                    .creationUser(users.get(random.nextInt(users.size())))
                    .issueType(IssueType.ELECTRICAL)
                    .priority(priorities[random.nextInt(priorities.length)])
                    .comments(new ArrayList<>())
                    .createDate(getRandomDate())
                    .status(statuses[random.nextInt(statuses.length)])
                    .build();
            app.getComments().add(CommentEntity.builder()
                    .content("Yello")
                    .dateTime(LocalDateTime.now())
                    .user(users.get(random.nextInt(users.size())))
                    .build());

//            System.err.println(app.toString());
            applicationRepository.save(app);
            appNum++;

        }


    }


}
