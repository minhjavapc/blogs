package com.vn.websocketwebflux.restController;

import com.vn.websocketwebflux.component.ReactiveWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

    private final ReactiveWebSocketHandler webSocketHandler;

    @Autowired
    public MessageController(ReactiveWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping("/sendToChanel/{message}")
    public Mono<Void> sendMessageToChanel(@PathVariable String message) {
        webSocketHandler.sendMessageToChanel(message);
        return Mono.empty();
    }
}