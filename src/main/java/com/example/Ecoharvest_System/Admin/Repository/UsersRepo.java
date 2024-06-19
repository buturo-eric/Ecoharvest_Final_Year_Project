package com.example.Ecoharvest_System.Admin.Repository;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<UsersModel, Integer> {
    public UsersModel findUsersModelByEmailAndPassword(String email, String password);
    public UsersModel findByEmail(String email);
}