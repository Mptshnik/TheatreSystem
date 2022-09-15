package com.system.theatre.repo;

import com.system.theatre.model.Performance;
import org.springframework.data.repository.CrudRepository;

public interface PerformanceRepository extends CrudRepository<Performance, Long>
{
}
