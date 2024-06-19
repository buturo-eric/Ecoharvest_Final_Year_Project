package com.example.Ecoharvest_System.Admin.Service;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;

    public List<UsersModel> getAll(){
        return usersRepo.findAll();
    }

    public UsersModel addUsersModel(UsersModel usersModel){
        usersModel.setPassword(usersModel.getPassword());
        usersModel.setRole(usersModel.getRole()); // Set default role as USER
        return usersRepo.save(usersModel);
    }

    public boolean checkUserCredentials(String email, String password) {
        UsersModel user = usersRepo.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public UsersModel updateUsersModel(UsersModel usersModel, Integer id){
        Optional<UsersModel> listUsersModel = usersRepo.findById(id);
        if(listUsersModel.isPresent()){
            UsersModel present = listUsersModel.get();
            present.setName(usersModel.getName());
            present.setEmail(usersModel.getEmail());
            present.setRole(usersModel.getRole());
            return usersRepo.save(present);
        }else {
            throw new RuntimeException("Id not Found");
        }
    }

    public void deleteUsersModel(Integer id){
        Optional<UsersModel> listUsersModel = usersRepo.findById(id);
        if(listUsersModel.isPresent()){
            UsersModel present = listUsersModel.get();
            usersRepo.delete(present);
        }else{
            throw new RuntimeException("Id not found");
        }
    }

    public UsersModel listById(Integer id){
        return usersRepo.findById(id).orElse(null);
    }

    public UsersModel userLogin(UsersModel usersModel){
        return usersRepo.findUsersModelByEmailAndPassword(usersModel.getEmail(), usersModel.getPassword());
    }
}

