package com.offco.calendar.controller;

import java.util.List;

import com.offco.calendar.dto.CalendarDto;
import com.offco.calendar.model.Calendar;
import com.offco.calendar.service.CalendarService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CalendarController {
    private final CalendarService calService;

    @PostMapping("/todolist")
    public void saveCalendar(@RequestBody CalendarDto infoDto) { // 회원 추가
        // Response response = new Response();

        try {
            calService.save(infoDto);
            // response.setResponse("success");
            // response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            // response.setResponse("failed");
            // response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            // response.setData(e.toString());
        }
        // return calService;
    }
}
