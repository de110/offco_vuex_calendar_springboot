package com.offco.calendar.repository;

import java.util.Optional;

import com.offco.calendar.model.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepo extends JpaRepository<Calendar, Long> {
    // Optional<Calendar> findByid(String id);
}
