package com.gmv2.pdv.service;

import com.gmv2.pdv.dto.UserDTO;
import com.gmv2.pdv.entity.User;
import com.gmv2.pdv.exceptions.NoItemException;
import com.gmv2.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<UserDTO>findAll(){
        return userRepository.findAll().stream().map(user -> new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user){
        User userSave= new User();
        userSave.setEnabled(user.isEnabled());
        userSave.setName(user.getName());
        userRepository.save(userSave);
        return new UserDTO(userSave.getId(), userSave.getName(), userSave.isEnabled());
    }

    public UserDTO findById(long id){
       Optional<User> optional = userRepository.findById(id);
       if(!optional.isPresent()){
           throw new NoItemException("Usuario não encontrado");
       }
       User user = optional.get();
       return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO update(UserDTO user){
        User userSave= new User();
        userSave.setEnabled(user.isEnabled());
        userSave.setName(user.getName());
        userSave.setId(user.getId());
        Optional<User> userToEdit = userRepository.findById(user.getId());
        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuario não encontrado");
        }
        userRepository.save(userSave);
        return new UserDTO(userSave.getId(), userSave.getName(), userSave.isEnabled());
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }
}
