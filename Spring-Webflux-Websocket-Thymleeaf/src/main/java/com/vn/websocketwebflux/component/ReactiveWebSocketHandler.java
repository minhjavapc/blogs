package com.vn.websocketwebflux.component;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.put(session.getId(), session);

        Sinks.Many<String> userSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> receive = session.receive().map(WebSocketMessage::getPayloadAsText);
        return session.send(
                        userSink.asFlux()
                                .mergeWith(receive)
                                .map(session::textMessage)
                )
                .doFinally(signalType -> {
                    sessions.remove(session.getId());
                });
    }

    public void sendMessageToChanel(String message) {
        sessions.values().forEach(session -> {
            session.send(Mono.just(session.textMessage(message))).subscribe();
        });
    }
}