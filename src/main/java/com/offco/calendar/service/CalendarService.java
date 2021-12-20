package com.offco.calendar.service;

import java.util.List;

import javax.transaction.Transactional;

import com.offco.calendar.dto.CalendarDto;
import com.offco.calendar.model.Calendar;
import com.offco.calendar.repository.CalendarRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//211220 코드 수정

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepo calendarRepo;

    // @Override
    // public List<Calendar> getAllDataList() {
    // return getAllDataList();
    // }

    @Transactional
    public Long save(CalendarDto infoDto) {
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return calendarRepo.save(Calendar.builder().title(infoDto.getTitle()).createdAt(infoDto.getCreatedAt())
                .build()).getId();
    }
}
