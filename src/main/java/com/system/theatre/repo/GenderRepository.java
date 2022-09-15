package com.system.theatre.repo;

import com.system.theatre.model.Gender;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender, Long>
{
}
