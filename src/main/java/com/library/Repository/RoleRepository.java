package com.library.Repository;

import com.library.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Authority,Integer> {
}
