package com.system.theatre.repo;

import com.system.theatre.model.User;
import org.springframework.data.repository.CrudRepository;

import java.nio.file.attribute.UserDefinedFileAttributeView;

public interface UserRepository extends CrudRepository<User, Long>
{
    public User findByUsername(String username);
}
