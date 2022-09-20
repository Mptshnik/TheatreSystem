package com.system.theatre.repo;

import com.system.theatre.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long>
{
    public Author findByLastname(String lastname);
}
