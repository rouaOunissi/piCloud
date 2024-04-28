package com.pi.ressources.Services;

import com.pi.ressources.entities.CalendarLine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CalendarLineService {
    List<CalendarLine> findAll();

    CalendarLine findById(Long id);

    List<String> getAllSpecialiteNames();

    Optional<CalendarLine> addCalendarLineFile(CalendarLine calendarLine, MultipartFile file);


    Optional<CalendarLine> UpCalendarLineFile(Long idCalendarLine, CalendarLine calendarLine, MultipartFile file);

    List<CalendarLine> getByIdUser(Long userid);

    ResponseEntity<?> deteleCalendarLine(Long id);

    ResponseEntity<byte[]> getCalendarPdf(Long id);
}
