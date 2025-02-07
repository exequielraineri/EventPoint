package com.exeraineri.eventpoint.client.domain.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Event {
    private Long id;
    private String title;
    private int image;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;

    public Event(Long id, String title, int image, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(Long id, String title, int image, LocalDateTime startDate, LocalDateTime endDate, String location) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Event(String title, int image, LocalDateTime startDate, String location) {
        this.title = title;
        this.image = image;
        this.startDate = startDate;
        this.location = location;
    }

    public Event() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getLocalDateFormat() {
        return startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyy"));
    }
}
