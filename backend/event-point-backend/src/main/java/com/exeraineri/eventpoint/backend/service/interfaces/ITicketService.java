/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.interfaces;

import com.exeraineri.eventpoint.backend.entity.Ticket;
import java.util.List;

/**
 *
 * @author Exequiel
 */
public interface ITicketService extends IBasicService<Ticket> {

    List<Ticket> saveAll(List<Ticket> tickets);

    List<Ticket> findByEventId(Long id);

    List<Ticket> findByUserId(Long id);
}
