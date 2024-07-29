package com.example.team.service;

import com.example.team.model.Manager;
import com.example.team.model.User;
import com.example.team.repository.ManagerJpaRepository;
import com.example.team.repository.UserJpaRepository;
import com.example.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserJpaService implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ManagerJpaRepository managerJpaRepository;

    public void validateMobileNumberAndAddToUser(String numberStr, User user){
        boolean isStartsWithZero = numberStr.startsWith("0");
        boolean isStartsWithPlusNineOne = numberStr.startsWith("+91");
        String mobileNumber = numberStr;

        if (isStartsWithZero) {
            mobileNumber = numberStr.substring(1);
        }

        if (isStartsWithPlusNineOne) {
            mobileNumber = numberStr.substring(3);
        }
        boolean isMatching = mobileNumber.matches("\\d{10}");

        if(isMatching){
            user.setMob_num(mobileNumber);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid mobile number details. It should only contain digits and may start with 0 or +91.");
        }

    }

    public void validatePanNumberAndAddToUser(String panNumber, User user){
        String convertedPanNumber = panNumber.toUpperCase();
        boolean isValidPanNumber = convertedPanNumber.matches("^[A-Z]{5}[0-9]{4}[A-Z]$");
        if (isValidPanNumber) {
            user.setPan_num(convertedPanNumber);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid pan number details.");
        }
    }

    @Override
    public User addUser(User user) {

        boolean ifAnyOneNull = (user.getFull_name() == null || user.getFull_name().isEmpty()) || (user.getMob_num() == null || user.getMob_num().isEmpty()) || (user.getPan_num() == null || user.getPan_num().isEmpty());

        if (ifAnyOneNull) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid data provided. Provide all the required fields.");
        }

        validateMobileNumberAndAddToUser(user.getMob_num(), user);
        validatePanNumberAndAddToUser(user.getPan_num(), user);

        if (user.getManager_id() != null) {
            Optional<Manager> manager = managerJpaRepository.findById(user.getManager_id());
            if (manager.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager not found. Provide valid manager id.");
            }
        }

        try {
            return userJpaRepository.save(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong. Check for any duplication in the data. Provide unique values for mobile number & Pan number.");
        }
    }

    @Override
    public List<User> getUsers(User user) {

        List<User> userList = new ArrayList<>();

        if(user != null){
            String mob_num = user.getMob_num();
            UUID user_id = user.getUser_id();
            UUID manager_id = user.getManager_id();

            if(mob_num != null){
                return userJpaRepository.findAll().stream()
                        .filter(each -> each.getMob_num().equals(mob_num))
                        .toList();
            }
            if(user_id != null){
                Optional<User> newUser = userJpaRepository.findById(user_id);
                newUser.ifPresent(userList::add);
                return userList;
            }
            if(manager_id != null){
                return userJpaRepository.findAll().stream().filter(each -> {
                    if(each.getManager_id() != null){
                        return each.getManager_id().equals(manager_id);
                    }
                    return false;
                }).toList();
            }
        }

        return userJpaRepository.findAll();
    }

    @Override
    public void deleteUser(User user) {
        if(user != null){
            String mob_num = user.getMob_num();
            UUID user_id = user.getUser_id();
            if(mob_num != null){
                Optional<User> userByMobileNum = userJpaRepository.findAll().stream().filter(each -> each.getMob_num().equals(mob_num)).findFirst();
                if(userByMobileNum.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found in this mobile number.");
                }else{
                    UUID userId = userByMobileNum.get().getUser_id();
                    userJpaRepository.deleteById(userId);
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Successfully deleted user.");
                }
            }
            if(user_id != null){
                Optional<User> userById = userJpaRepository.findById(user_id);
                if(userById.isPresent()){
                    userJpaRepository.deleteById(user_id);
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Successfully deleted user.");
                }else{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid user id.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide valid data to delete the user");
    }

    @Override
    public List<User> updateUsers(List<UUID> userIds, User user) {

        boolean isOtherFieldsAreNull = (user.getFull_name() == null || user.getFull_name().isEmpty()) && (user.getMob_num() == null || user.getMob_num().isEmpty()) && (user.getPan_num() == null || user.getPan_num().isEmpty());
        boolean isManagerIdPresent = user.getManager_id() != null;
        boolean isOnlyOneUserIsPresent = userIds.size() == 1;

        if(isManagerIdPresent && isOtherFieldsAreNull){

            userIds.forEach(eachId -> {
                Optional<User> userById = userJpaRepository.findById(eachId);
                Optional<Manager> managerById = managerJpaRepository.findById(user.getManager_id());

                if(userById.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user Id's provided. User not found.");
                }
                if(managerById.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid manager Id provided. Manager not found");
                }

                if(userById.get().getManager_id() == null){
                    userById.get().setManager_id(user.getManager_id());
                }else{
                    userById.get().setManager_id(user.getManager_id());
                    userById.get().setIs_active(false);
                }

                userJpaRepository.save(userById.get());
            });
            return userJpaRepository.findAllById(userIds);

        }
        if(!isOtherFieldsAreNull && !isOnlyOneUserIsPresent){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update personal details as individual request. Bulk update is only for updating user manager Id's.");
        }
        if(isOnlyOneUserIsPresent){
            UUID requestUserId = userIds.get(0);
            Optional<User> requestUser = userJpaRepository.findById(requestUserId);

            if(requestUser.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            User currentUser = requestUser.get();
            UUID requestUserManagerId = currentUser.getManager_id();
            Optional<Manager> requestUserManager = managerJpaRepository.findById(requestUserManagerId);

            if(requestUserManager.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user manager not found");
            }

            if(user.getFull_name() != null && !user.getFull_name().isEmpty()){
                currentUser.setFull_name(user.getFull_name());
            }

            if(user.getMob_num() != null && !user.getMob_num().isEmpty()){
                validateMobileNumberAndAddToUser(user.getMob_num(), currentUser);
            }

            if(user.getPan_num() != null && !user.getPan_num().isEmpty()){
                validatePanNumberAndAddToUser(user.getPan_num(), currentUser);
            }

            if(isManagerIdPresent && currentUser.getManager_id() != null){
                currentUser.setManager_id(user.getManager_id());
                currentUser.setIs_active(false);
            } else if (isManagerIdPresent && currentUser.getManager_id() == null) {
                currentUser.setManager_id(user.getManager_id());
            }
            userJpaRepository.save(currentUser);
        }
        return userJpaRepository.findAllById(userIds);
    }
}
