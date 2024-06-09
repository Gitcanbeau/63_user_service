package com.codedecode.userinfo.mapper;

import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//    this is how you're going to create an instance of the same interface
//     and using this instance, you're going to
//     call all the methods which we're going to write in this interface, which is the abstarct method
//    implementation of this interface will be created automatically by Spring Boot
//    which will be having all the setters/getters/mapping between the entity to DTOdata and DTOdata to entity
    User mapUserDTOToUser(UserDTO userDTO);
    UserDTO mapUserToUserDTO(User user);

}
