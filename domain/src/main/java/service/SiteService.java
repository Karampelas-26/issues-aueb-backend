package service;

import entity.SiteEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
    public List<SiteEntity> allSites;
    @PostConstruct
    public void init(){}
}
