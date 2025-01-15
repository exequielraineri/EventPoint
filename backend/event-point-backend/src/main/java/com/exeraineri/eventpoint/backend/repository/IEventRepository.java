/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.exeraineri.eventpoint.backend.repository;

import com.exeraineri.eventpoint.backend.entity.Event;
import com.exeraineri.eventpoint.backend.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Exequiel
 */
@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategoryId(Long id);

    List<Event> findByOrganizerId(Long id);
}
