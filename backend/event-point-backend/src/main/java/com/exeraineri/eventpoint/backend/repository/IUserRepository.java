/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.repository;

import com.exeraineri.eventpoint.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Exequiel
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    public boolean existsById(Long id);

    boolean existsByEmail(String email);

}
