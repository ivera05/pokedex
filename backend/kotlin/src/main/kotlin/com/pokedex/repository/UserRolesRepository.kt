package com.pokedex.repository

import com.pokedex.entity.UserRoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRolesRepository : JpaRepository<UserRoleEntity, Long>
