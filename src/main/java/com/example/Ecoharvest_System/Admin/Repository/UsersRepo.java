package com.example.Ecoharvest_System.Admin.Repository;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsersRepo extends JpaRepository<UsersModel, Integer> {
    public UsersModel findUsersModelByEmailAndPassword(String email, String password);
    public UsersModel findByEmail(String email);
    long countByCreationDate(LocalDate date);

    List<UsersModel> findByCreationDate(LocalDate date);
    List<UsersModel> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);


    // Add this method to count users from the previous day
    @Query("SELECT COUNT(u) FROM UsersModel u WHERE u.creationDate BETWEEN :start AND :end")
    long countUsersBetweenDates(LocalDate start, LocalDate end);

}