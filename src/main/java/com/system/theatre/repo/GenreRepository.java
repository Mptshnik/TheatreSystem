package com.system.theatre.repo;

import com.system.theatre.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long>
{
}
