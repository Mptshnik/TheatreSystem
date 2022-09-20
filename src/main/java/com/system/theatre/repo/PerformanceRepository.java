package com.system.theatre.repo;

import com.system.theatre.model.Performance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerformanceRepository extends CrudRepository<Performance, Long>
{
    public List<Performance> findByNameContains(String name);
}
