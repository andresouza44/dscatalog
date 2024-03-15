package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.UserDto;
import com.andresouza.dscatalog.dto.UserInsertDTO;
import com.andresouza.dscatalog.entities.Role;
import com.andresouza.dscatalog.entities.User;
import com.andresouza.dscatalog.repositories.RoleRepository;
import com.andresouza.dscatalog.repositories.UserRepository;
import com.andresouza.dscatalog.servicies.exceptions.DataBaseException;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repostitory;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> list = repostitory.findAll(pageable);

        return list.map(e -> new UserDto(e));
    }

    @Transactional(readOnly = true)
    public UserDto findById (Long id){
        User entity = repostitory.findById(id).orElseThrow( ()->
                new ResourceNotFoundException("Resource not found"));

        return new UserDto(entity);

    }

    @Transactional
    public UserDto insert (UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        repostitory.save(entity);

        return new UserDto(entity);

    }

    @Transactional
    public UserDto update (Long id, UserDto dto){
        try {
            User entity = repostitory.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            repostitory.save(entity);
            return  new UserDto(entity);
        }
        catch (EntityNotFoundException e ){
            throw  new ResourceNotFoundException("Resource not found");
        }
    }

    @Transactional
    public void  delete (Long id){
        if (!repostitory.existsById(id)){
            throw new ResourceNotFoundException("Resource not found");
        }
        try {
            repostitory.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Data Integrity Violation Exception");
        }
    }
    private void  copyDtoToEntity(UserDto dto, User entity){
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());

        entity.getRoles().clear();
        dto.getRoles().forEach(roleDto -> {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
            });

    }
}


