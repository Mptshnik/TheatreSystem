package com.system.theatre.repo;

import com.system.theatre.model.SceneRole;
import org.springframework.data.repository.CrudRepository;

public interface SceneRoleRepository extends CrudRepository<SceneRole, Long> {
    public SceneRole findByName(String name);
}
