package com.example.Ecoharvest_System.Admin.Repository;

import com.example.Ecoharvest_System.Admin.Model.CommunityEducationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityEducationRepository extends JpaRepository<CommunityEducationModel, Long> {
}
