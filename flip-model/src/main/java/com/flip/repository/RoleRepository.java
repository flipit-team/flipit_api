package com.flip.repository;

import com.flip.entity.Role;
import com.flip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author just4 on 09/09/2021
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> getRolesByIdIn(List<Long> roleIds);
}
