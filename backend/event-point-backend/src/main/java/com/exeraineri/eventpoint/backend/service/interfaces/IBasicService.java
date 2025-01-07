/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.exeraineri.eventpoint.backend.service.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Exequiel
 * @param <T>
 */
public interface IBasicService<T> {

    @Transactional(readOnly = true)
    List<T> findAll();

    @Transactional(readOnly = true)
    T findById(Long id);

    @Transactional
    T save(T elem);

    @Transactional
    T update(Long id, T elem);

    @Transactional
    void deleteById(Long id);

}
