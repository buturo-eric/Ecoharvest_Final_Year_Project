package com.example.Ecoharvest_System.Admin.Service;

import com.example.Ecoharvest_System.Admin.Model.CommunityEducationModel;
import com.example.Ecoharvest_System.Admin.Repository.CommunityEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityEducationService {

    @Autowired
    private CommunityEducationRepository communityEducationRepository;

    @Transactional
    public CommunityEducationModel saveCommunityEducation(CommunityEducationModel communityEducation) {
        return communityEducationRepository.save(communityEducation);
    }

    public CommunityEducationModel getCommunityEducationById(Long id) {
        return communityEducationRepository.findById(id).orElseThrow(() -> new RuntimeException("Community Education not found"));
    }

    public List<CommunityEducationModel> getAllCommunityEducations() {
        return communityEducationRepository.findAll();
    }

    @Transactional
    public void deleteCommunityEducation(Long id) {
        communityEducationRepository.deleteById(id);
    }
}

