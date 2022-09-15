package com.system.theatre.repo;

import com.system.theatre.model.Membership;
import org.springframework.data.repository.CrudRepository;

public interface MembershipRepository extends CrudRepository<Membership, Long>
{
}
