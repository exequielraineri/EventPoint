/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.interfaces;

import com.exeraineri.eventpoint.backend.entity.Event;
import java.util.List;

/**
 *
 * @author Exequiel
 */
public interface IEventService extends IBasicService<Event> {

    List<Event> findByCategoryId(Long id);

    List<Event> findByOrganizerId(Long id);
}
