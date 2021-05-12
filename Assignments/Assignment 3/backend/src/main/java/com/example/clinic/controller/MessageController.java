package com.example.clinic.controller;

import com.example.clinic.dto.message.DoctorMessage;
import com.example.clinic.dto.message.SecretaryMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import static com.example.clinic.UrlMapping.*;

@Controller
public class MessageController {
    @MessageMapping(MESSAGE)
    @SendTo(TOPIC + ALERTS)
    public DoctorMessage alert(SecretaryMessage message) throws Exception {
        String doctor = HtmlUtils.htmlEscape(message.getDoctorName());
        String date = HtmlUtils.htmlEscape(message.getDate());
        String patient = HtmlUtils.htmlEscape(message.getPatientName());
        return new DoctorMessage(doctor + ", you have an appointment at " + date + " with " + patient);
    }
}
