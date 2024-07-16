package com.library.Repository;

import com.library.Entity.UserAndRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAndRoleRepository extends JpaRepository<UserAndRole, Integer> {
}
