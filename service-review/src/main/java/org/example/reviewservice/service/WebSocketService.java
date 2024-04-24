package org.example.reviewservice.service;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @SendTo("/destinations")
    public void sendMessageToTopicDestinations(String message) {
        messagingTemplate.convertAndSend("/destinations", message);
    }
    @SendTo("/routes")
    public void sendMessageToTopicRoutes(String message) {
        messagingTemplate.convertAndSend("/routes", message);
    }
    @SendTo("/schedule")
    public void sendMessageToTopicSchedule(String message) {
        messagingTemplate.convertAndSend("/schedule", message);
    }
    @SendTo("/booking")
    public void sendMessageToTopicBooking(String message) {
        messagingTemplate.convertAndSend("/booking", message);
    }
}